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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mariza.customer.mapper.CustomerMapper;
import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.entity.Customer;
import com.mariza.customer.exceptions.ResourceNotFoundException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerServiceInterface{

        private final CustomerMapper customerMapper;
        private final CustomerRepository customerRepository;
        private static final Logger log = LoggerFactory.getLogger(CustomerServiceImplementation.class);
        private static final Logger auditLogger =
            LoggerFactory.getLogger("AUDIT");

        @Value("${booking-service.url}")
        private String bookingServiceUrl;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
      //  auditLogger.info("AUDIT TEST");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(createCustomerRequest.getPassword());

        Customer customer = customerMapper.toEntity(createCustomerRequest);
        customer.setPasswordHash(hashedPassword);

        Customer savedCustomer = customerRepository.save(customer);
        auditLogger.info("Created customer successfully"+
        "id: " + savedCustomer.getId() +
        "created at: " + savedCustomer.getCreatedAt());
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            auditLogger.info("Customer with id: " + id + " is not found");
            throw new ResourceNotFoundException("Customer not found " + "id: " + id);
        }
        return customerMapper.toResponse(customer.get());
    }

    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest updateCustomerRequest) {
        Optional <Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            auditLogger.warn("Customer with id: " + id + " is not found");
            throw new ResourceNotFoundException("Customer not found ");
        }

        customerMapper.updateEntityFromDto(updateCustomerRequest, customer.get());
        Customer updatedCustomer = customerRepository.save(customer.get());
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
                .orElseThrow(() -> {
                    log.warn("Delete failed | customer not found | id={}",id);
                   return new ResourceNotFoundException("Customer not found id: " + id);
                });

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
          auditLogger.warn("Customer with id: "+ id + " has active bookings and can not be deleted");
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Customer has active bookings | Delete Blocked");

        }

        customerRepository.delete(customer);
        auditLogger.info("Customer with id: " + id + " has been deleted");
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
