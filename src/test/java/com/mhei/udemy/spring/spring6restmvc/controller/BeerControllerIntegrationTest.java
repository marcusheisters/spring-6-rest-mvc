package com.mhei.udemy.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.model.BeerStyle;
import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.hamcrest.core.IsNull;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();
    }

    @Test
    void testListBeers() {
        List<BeerDTO> beers = beerController.listBeers(null, null, false);
        assertThat(beers).hasSize(2410);
    }

    @Test
    @SneakyThrows
    void testListBeersByName() {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }

    @Test
    @SneakyThrows
    void testListBeersByStyle() {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerStyle", BeerStyle.PILSNER.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1280)));
    }

    @Test
    @SneakyThrows
    void testListBeersByNameAndStyle() {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
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
        List<BeerDTO> beers = beerController.listBeers(null, null, false);
        assertThat(beers).isEmpty();
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
        BeerDTO beerDTO = beerController.listBeers(null,null, false).get(0);
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


    @Test
    @SneakyThrows
    void useQueryParametersShouldReturnCorrectAmountOfBeers() {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
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

    @Test
    void testListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("beerName", "IPA")
                        .queryParam("showInventory", "true")
                        .queryParam("pageNumber", "2")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(50)))
                .andExpect(jsonPath("$[0].beerName", is("Sierra Nevada Torpedo Extra IPA")))
                .andExpect(jsonPath("$[0].quantityOnHand").value(IsNull.notNullValue()));

    }

}