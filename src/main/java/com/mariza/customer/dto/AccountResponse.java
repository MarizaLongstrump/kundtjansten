package com.mariza.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AccountResponse {
    private Long id;
    private String email;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
