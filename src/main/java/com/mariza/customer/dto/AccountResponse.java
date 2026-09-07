package com.mariza.customer.dto;

import java.time.LocalDateTime;

public class AccountResponse {
    private Long id;
    private String email;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccountResponse(Long id,
                           String email,
                           Long customerId,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public Long getCustomerId() { return customerId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}
