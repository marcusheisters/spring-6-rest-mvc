package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<BeerDTO> beers = beerController.listBeers();
        assertThat(beers.size()).isEqualTo(3);
    }

    @Test
    void testGetBeerById() {
        BeerDTO beer = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beers = beerController.listBeers();
        assertThat(beers.size()).isEqualTo(0);
    }

    @Transactional
    @Rollback
    @Test
    void testSaveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<HttpStatus> responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] url = responseEntity.getHeaders().getLocation().toString().split("/");
        UUID beerId = UUID.fromString(url[4]);
        Beer beer = beerRepository.findById(beerId).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testUpdateExistingBeer() {
        BeerDTO beerDTO = beerController.listBeers().get(0);
        beerDTO.setBeerName("Updated Beer");

        ResponseEntity<HttpStatus> responseEntity = beerController.updateBeerById(beerDTO.getId(), beerDTO);

        Beer beer = beerRepository.findById(beerDTO.getId()).get();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beer.getBeerName()).isEqualTo("Updated Beer");
    }

}