package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.Beer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Service
public interface BeerService {

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void deleteById(UUID beerId);

    void updateBeerById(UUID beerId, Beer beer);

    void patchBeerById(UUID beerId, Beer beer);
}
