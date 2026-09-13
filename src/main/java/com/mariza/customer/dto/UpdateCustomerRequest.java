package com.mariza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UpdateCustomerRequest {

    private String phoneNumber;
    private String address;
    private String city;

}
