package com.mariza.customer.service;

import com.mariza.customer.dto.BookingResponse;
import com.mariza.customer.exceptions.ResourceNotFoundException;
import com.mariza.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import com.mariza.customer.mapper.CustomerMapper;
import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.entity.Customer;
import com.mariza.customer.exceptions.ResourceNotFoundException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerServiceInterface{

        private final CustomerMapper customerMapper;
        private final CustomerRepository customerRepository;

        @Value("${booking-service.url}")
        private String bookingServiceUrl;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(createCustomerRequest.getPassword());

        Customer customer = customerMapper.toEntity(createCustomerRequest);
        customer.setPasswordHash(hashedPassword);

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        customerMapper.updateEntityFromDto(updateCustomerRequest, customer);
        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


        String url = bookingServiceUrl + "/booking/customer/" + id;
        //String url = "http://booking-service:8080/booking/customer/" + id;

        RestTemplate restTemplate = new RestTemplate();
        BookingResponse[] bookings;
        try {
            ResponseEntity<BookingResponse[]> response = restTemplate.getForEntity(url, BookingResponse[].class);
            bookings = response.getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Booking service unavailable. Details: " + ex.getMessage());
        }

        if (bookings != null && bookings.length > 0) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Customer has active bookings");
        }

        customerRepository.delete(customer);
    }

    @Override
    public Customer login(String email, String password) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        if (!customer.getPasswordHash().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        return customer;
    }

}
