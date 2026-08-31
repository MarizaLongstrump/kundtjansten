package com.mariza.customer.service;

import com.mariza.customer.exceptions.ResourceNotFoundException;
import com.mariza.customer.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import com.mariza.customer.mapper.CustomerMapper;
import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.entity.Customer;
import com.mariza.customer.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerServiceInterface{


        private final CustomerMapper customerMapper;
        private final CustomerRepository customerRepository;

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
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
}

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

}
