package com.mhei.udemy.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    WebApplicationContext wac;
    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", RandomString.make(120));

        MvcResult mvcResult = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();
    }

    @Test
    void testListBeers() {
        List<BeerDTO> beers = beerController.listBeers();
        assertThat(beers.size()).isEqualTo(2410);
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

    @Transactional
    @Rollback
    @Test
    void testUpdateExistingBeer() {
        BeerDTO beerDTO = beerController.listBeers().get(0);
        beerDTO.setBeerName("Updated Beer");

        ResponseEntity<HttpStatus> responseEntity = beerController.updateBeerById(beerDTO.getId(), beerDTO);

        Beer beer = beerRepository.findById(beerDTO.getId()).get();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void shouldThrowNotFoundException() {
        Assertions.assertThrows(NotFoundException.class,
                () -> beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity<HttpStatus> responseEntityResponseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntityResponseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Optional<Beer> foundBeer = beerRepository.findById(beer.getId());
        assertThat(foundBeer).isEmpty();

    }

    @Test
    void deleteByIdNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> beerController.deleteById(UUID.randomUUID()));
    }

}