package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.Beer;
import com.mhei.udemy.spring.spring6restmvc.services.BeerService;
import com.mhei.udemy.spring.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author marcusheisters
 */
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Test
    void getBeerById() throws Exception {

        beerServiceImpl = new BeerServiceImpl();
        Beer beer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(beer.getId())).willReturn(beer);
        mockMvc.perform(get("/api/v1/beer/" + beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$.id"), is(beer.getId().toString())))
                .andExpect(jsonPath(("$.beerName"), is(beer.getBeerName())));

    }

}