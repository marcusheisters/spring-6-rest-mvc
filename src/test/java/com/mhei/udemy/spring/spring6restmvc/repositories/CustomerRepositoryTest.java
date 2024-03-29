package com.mhei.udemy.spring.spring6restmvc.repositories;

import com.mhei.udemy.spring.spring6restmvc.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


/**
 * @author marcusheisters
 */
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomerTest() {
        Customer savedCustomer = new Customer();
        savedCustomer.setCustomerName("Ernie");
        customerRepository.save(savedCustomer);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isNotNull();
    }

}