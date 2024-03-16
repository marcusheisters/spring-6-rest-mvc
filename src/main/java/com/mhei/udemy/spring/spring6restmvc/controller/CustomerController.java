package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.Customer;
import com.mhei.udemy.spring.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@RestController
@AllArgsConstructor
public class CustomerController {
    private final String CUSTOMER_PATH = "/api/v1/customer";
    private final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> handleUpdate(@PathVariable("customerId") UUID customerId,
            @RequestBody Customer customer)
    {
        customerService.updateCustomerById(customerId, customer);
        getCustomerById(customerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "api/v1/customer" + customer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteCustomerById(
            @PathVariable("customerId") UUID customerId)
    {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> patchCustomerById(@PathVariable("customerId") UUID customerId,
            @RequestBody Customer customer)
    {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
