package com.mhei.udemy.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.services.BeerService;
import com.mhei.udemy.spring.spring6restmvc.services.BeerServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author marcusheisters
 */
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Captor
            ArgumentCaptor<BeerDTO> beerArgumentCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeerById() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "newName");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID,beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap).containsEntry("beerName", beerArgumentCaptor.getValue().getBeerName());

    }
    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);
        beer.setId(null);
        beer.setVersion(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null).get(1));

    mockMvc.perform(post(BeerController.BEER_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));

    }

    @Test
    void updateBeerTest() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);

        given(beerService.updateBeerById(any(UUID.class), any(BeerDTO.class)))
                .willReturn(Optional.of(beer));
        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())

                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                        .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testGetBeerById() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);

        given(beerService.getBeerById(beer.getId())).willReturn(Optional.of(beer));
        mockMvc.perform(get(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$.id"), is(beer.getId().toString())))
                .andExpect(jsonPath(("$.beerName"), is(beer.getBeerName())));

    }

    @Test
    @SneakyThrows
    void createBeerWithoutRequiredPropsShouldShouldFail() {
        BeerDTO beerDTO =
                BeerDTO.builder().build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null).get(0));

       mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)));
    }

    @Test
    void testDeleteBeet() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);
        given(beerService.deleteById(any())).willReturn(true);
        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

 @Test
 void getBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class)))
                .willReturn(Optional.empty());
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void updateBeerWithoutRequiredPropsShouldFail() {
        BeerDTO beer = beerServiceImpl.listBeers(null).get(0);
        beer.setBeerName(null);
        beer.setBeerStyle(null);
        beer.setPrice(null);
        beer.setUpc(null);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)));
    }

    @Test
    @SneakyThrows
    void testListBeers() {
        given(beerService.listBeers(null)).willReturn(beerServiceImpl.listBeers(null));
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

    }}