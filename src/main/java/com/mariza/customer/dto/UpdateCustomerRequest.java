package com.mariza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCustomerRequest {

    private String firstName;
    private String lastName;
    private String phone;
}
