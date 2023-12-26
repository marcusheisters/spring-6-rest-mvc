package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.Beer;
import com.mhei.udemy.spring.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("api/v1/beer")
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id) {

        log.debug("Get beer by id was called - in Controller");

        return beerService.getBeerById(id);

    }
}
