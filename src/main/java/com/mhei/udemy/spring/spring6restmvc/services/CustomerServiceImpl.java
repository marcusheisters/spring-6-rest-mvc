package com.mhei.udemy.spring.spring6restmvc.services;
import com.mhei.udemy.spring.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author marcusheisters
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Bill Edwards")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Tiff Taylor")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Parka Pete")
                .version(1)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .version(1)
                .name(customer.getName())
                .build();

        customerMap.put(customer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO updatedCustomer) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setName(updatedCustomer.getName());
        existing.setVersion(updatedCustomer.getVersion());
        existing.setUpdatedOn(LocalDateTime.now());
        return Optional.ofNullable(existing);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
        return null;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }
    }
}
