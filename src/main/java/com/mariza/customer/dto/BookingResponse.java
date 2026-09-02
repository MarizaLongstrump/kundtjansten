package com.mariza.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse {
    private Long id;
    private Long customerId;
    private Long roomId;
    private String checkIn;
    private String checkOut;
}
