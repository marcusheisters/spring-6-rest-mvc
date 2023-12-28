package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Service
public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);
}
