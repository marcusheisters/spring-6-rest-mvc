package com.mhei.udemy.spring.spring6restmvc.bootstrap;

import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import com.mhei.udemy.spring.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author marcusheisters
 */
@DataJpaTest
class BootstrapDataTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() throws Exception {
        bootstrapData = new BootstrapData(beerRepository, customerRepository);
       bootstrapData.run();
    }

    @Test
    void testRun() throws Exception {
       bootstrapData.run();
        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}