package com.mhei.udemy.spring.spring6restmvc.mappers;

import com.mhei.udemy.spring.spring6restmvc.entities.Customer;
import com.mhei.udemy.spring.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * @author marcusheisters
 */
@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
