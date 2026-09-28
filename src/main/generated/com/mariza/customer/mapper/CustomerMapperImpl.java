package com.mariza.customer.mapper;

import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.CustomerResponse;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-28T14:25:12+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CreateCustomerRequest request) {
        if ( request == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setEmail( request.getEmail() );
        customer.setFirstName( request.getFirstName() );
        customer.setLastName( request.getLastName() );
        customer.setPhoneNumber( request.getPhoneNumber() );
        customer.setAddress( request.getAddress() );
        customer.setCity( request.getCity() );

        return customer;
    }

    @Override
    public CustomerResponse toResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setId( customer.getId() );
        customerResponse.setFirstName( customer.getFirstName() );
        customerResponse.setLastName( customer.getLastName() );
        customerResponse.setEmail( customer.getEmail() );
        customerResponse.setPhoneNumber( customer.getPhoneNumber() );
        customerResponse.setAddress( customer.getAddress() );
        customerResponse.setCity( customer.getCity() );
        customerResponse.setCreatedAt( customer.getCreatedAt() );
        customerResponse.setUpdatedAt( customer.getUpdatedAt() );

        return customerResponse;
    }

    @Override
    public void updateEntityFromDto(UpdateCustomerRequest request, Customer customer) {
        if ( request == null ) {
            return;
        }

        customer.setPhoneNumber( request.getPhoneNumber() );
        customer.setAddress( request.getAddress() );
        customer.setCity( request.getCity() );
    }
}
