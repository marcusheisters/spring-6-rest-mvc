package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.Customer;
import com.mhei.udemy.spring.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<HttpStatus> handlePost(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<HttpStatus> handleUpdate(@PathVariable("customerId") UUID customerId,
            @RequestBody Customer customer)
    {
        HttpHeaders headers = new HttpHeaders();
        Customer existing = getCustomerById(customerId);
        existing.setCustomerName(customer.getCustomerName());
        existing.setVersion(existing.getVersion() + 1);
        existing.setCreatedOn(LocalDateTime.now());
        existing.setUpdatedOn(LocalDateTime.now());

        headers.add("Location", "api/v1/customer" + customerId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<HttpStatus> deleteCustomerById(
            @PathVariable("customerId") UUID customerId)
    {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity<HttpStatus> patchCustomerById(@PathVariable("customerId") UUID customerId,
            @RequestBody Customer customer)
    {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
