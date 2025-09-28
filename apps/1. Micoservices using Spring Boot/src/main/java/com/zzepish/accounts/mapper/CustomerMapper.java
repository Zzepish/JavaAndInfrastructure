package com.zzepish.accounts.mapper;

import com.zzepish.accounts.dto.CustomerDTO;
import com.zzepish.accounts.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public static CustomerDTO mapCustomerToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setName(customer.getName());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }

    public static Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setName(customerDTO.getName());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());

        return customer;
    }
}
