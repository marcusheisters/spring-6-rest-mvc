package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.CustomerDTO;
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
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> handleUpdate(@PathVariable("customerId") UUID customerId,
            @RequestBody CustomerDTO customer)
    {
        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable("customerId") UUID customerId)
    {

        if (!customerService.deleteCustomerById(customerId)) {
            throw new NotFoundException();
        }
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> patchCustomerById(@PathVariable("customerId") UUID customerId,
            @RequestBody CustomerDTO customer)
    {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
