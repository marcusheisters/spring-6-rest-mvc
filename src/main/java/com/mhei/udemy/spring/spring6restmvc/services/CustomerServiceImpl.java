package com.mhei.udemy.spring.spring6restmvc.services;
import com.mhei.udemy.spring.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author marcusheisters
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Bill Edwards")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Tiff Taylor")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Parka Pete")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .version(1)
                .customerName(customer.getCustomerName())
                .build();

        customerMap.put(customer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Customer updateCustomerById(UUID customerId,Customer updatedCustomer) {
        Customer customer = getCustomerById(customerId);
        customer.setCustomerName(updatedCustomer.getCustomerName());
        customer.setVersion(updatedCustomer.getVersion());
        customer.setUpdatedOn(LocalDateTime.now());
        return customer;
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {
        Customer existing = getCustomerById(customerId);
        if (StringUtils.hasText(customer.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
        }
    }
}
