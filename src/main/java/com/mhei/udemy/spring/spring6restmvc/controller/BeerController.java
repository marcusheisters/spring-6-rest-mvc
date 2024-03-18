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
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}" ;
    private final BeerService beerService;

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<ResponseEntity<HttpStatus>> deleteById(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(BEER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody Beer beer) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", BEER_PATH_ID);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH_ID)
   public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get beer by id was called - in Controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);

    }

  @PutMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
