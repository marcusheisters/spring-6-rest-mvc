package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Service
public interface CustomerService {

    List<Customer> listCustomers();
    Optional<Customer> getCustomerById(UUID id);
    Customer saveCustomer(Customer customer);
    void updateCustomerById(UUID customerId,Customer customer);
    void deleteCustomerById(UUID customerId);
    void patchCustomerById(UUID customerId, Customer customer);

}
