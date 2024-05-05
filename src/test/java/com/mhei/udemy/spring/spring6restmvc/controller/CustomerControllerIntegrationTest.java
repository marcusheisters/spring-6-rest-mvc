package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.entities.Customer;
import com.mhei.udemy.spring.spring6restmvc.mappers.CustomerMapper;
import com.mhei.udemy.spring.spring6restmvc.model.CustomerDTO;
import com.mhei.udemy.spring.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testListCustomers() {
        List<CustomerDTO> customers = customerController.listCustomers();
        assertThat(customers.size()).isEqualTo(3);
    }

    @Test
    void getCustomerById() {
        CustomerDTO customer = customerController.getCustomerById(customerRepository.findAll().get(0).getId());
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("New Customer")
                .build();
        ResponseEntity<HttpStatus> responseEntity = customerController.handlePost(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] url = responseEntity.getHeaders().getLocation().toString().split("/");
        UUID customerId = UUID.fromString(url[4]);
        Customer customer = customerRepository.findById(customerId).get();
        assertThat(customer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateExistingCustomer() {
        CustomerDTO customerDTO = customerController.listCustomers().get(0);
        customerDTO.setName("Updated customer");

        ResponseEntity<HttpStatus> responseEntity =
                customerController.handleUpdate(customerDTO.getId(), customerDTO);

        Customer customer = customerRepository.findById(customerDTO.getId()).get();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customer.getCustomerName()).isEqualTo("Updated customer");
    }
    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity<HttpStatus> responseEntityResponseEntity =
                customerController.deleteById(customer.getId());
        assertThat(responseEntityResponseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Optional<CustomerDTO> foundCustomer = customerRepository.findById(customer.getId()).map(customerMapper::customerToCustomerDto);
        assertThat(foundCustomer).isEmpty();

    }


    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> customerController.deleteById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.listCustomers();
        assertThat(customers.size()).isEqualTo(0);
    }

}
