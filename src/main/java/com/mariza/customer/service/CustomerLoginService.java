package com.mariza.customer.service;

import com.mariza.customer.dto.AccountResponse;
import com.mariza.customer.entity.Customer;
import com.mariza.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerLoginService {

    private final CustomerRepository customerRepository;


    public AccountResponse login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElse(null);
        if (customer == null) {
            return null; // fel email
        }
        boolean passwordMatch = BCrypt.checkpw(password, customer.getPasswordHash());
        if (!passwordMatch) {
            return null;
        }
        return new AccountResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getId(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

}
