package com.mariza.customer.controller.api;

import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.service.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceInterface customerService;

    // Create
    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
        return customerService.createCustomer(createCustomerRequest);
    }

    // Get
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    // update
    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id,
                                           @RequestBody UpdateCustomerRequest updateCustomerRequest     ){
    return customerService.updateCustomer(id, updateCustomerRequest);
    }

    // delete
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
