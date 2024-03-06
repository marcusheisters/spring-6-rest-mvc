package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.model.Beer;
import com.mhei.udemy.spring.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @DeleteMapping("{beerId}")
    public ResponseEntity<ResponseEntity<HttpStatus>> deleteById(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "api/v1/beer" + savedBeer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
   public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get beer by id was called - in Controller");
        return beerService.getBeerById(beerId);

    }

  @PutMapping("{beerId}")
    public ResponseEntity<HttpStatus> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
