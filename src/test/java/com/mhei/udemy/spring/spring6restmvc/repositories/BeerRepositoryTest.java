package com.mhei.udemy.spring.spring6restmvc.repositories;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


/**
 * @author marcusheisters
 */
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
            void saveBeerTest() {
        Beer savedBeer = new Beer();
        savedBeer.setBeerName("Franziskaner");
        beerRepository.save(savedBeer);


        Assertions.assertThat(savedBeer).isNotNull();
        Assertions.assertThat(savedBeer.getId()).isNotNull();
    }

}