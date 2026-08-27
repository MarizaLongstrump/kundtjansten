package com.mariza.customer.service;

import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.dto.UpdateCustomerRequest;

public interface CustomerServiceInterface {

    //Det här är metoden som skapar en kund.
    CustomerResponse createCustomer(CreateCustomerRequest request);
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);
    CustomerResponse getCustomerById(Long id);
    void deleteCustomer(Long id);
}
