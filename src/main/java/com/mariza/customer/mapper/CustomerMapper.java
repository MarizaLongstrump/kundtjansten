package com.mariza.customer.mapper;

import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface CustomerMapper {

    Customer toEntity(CreateCustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    void updateEntityFromDto(UpdateCustomerRequest request, @MappingTarget Customer customer);
}
