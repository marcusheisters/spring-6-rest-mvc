package com.mhei.udemy.spring.spring6restmvc.repositories;

import com.mhei.udemy.spring.spring6restmvc.bootstrap.BootstrapData;
import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerStyle;
import com.mhei.udemy.spring.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerByName() {
        List<Beer> beers = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        assertThat(beers).hasSize(336);
    }

    @Test
    void saveBeerWithTooLongBeerNameShouldThrowException() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            // Given
            String beerName = RandomString.make(120);
        Beer beer = Beer.builder()
                .beerName(beerName)
                .beerStyle(BeerStyle.IPA)
                .upc("123456789012L")
                .price(BigDecimal.ONE)
                .build();
        beerRepository.save(beer);
       beerRepository.flush();
        });
    }

    @Test
    void saveNewBeerShouldSucceed() {
        Beer beer = Beer.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789012L")
                .price(BigDecimal.ONE)
                .build();
        beerRepository.save(beer);

        beerRepository.flush();
        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

}