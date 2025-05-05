package com.mhei.udemy.spring.spring6restmvc.bootstrap;

import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import com.mhei.udemy.spring.spring6restmvc.repositories.CustomerRepository;
import com.mhei.udemy.spring.spring6restmvc.services.BeerCsvService;
import com.mhei.udemy.spring.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author marcusheisters
 */
@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() throws Exception {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
       bootstrapData.run();
    }

    @Test
    void testRun() throws Exception {
       bootstrapData.run();
        assertThat(beerRepository.count()).isEqualTo(2410);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}