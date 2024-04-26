package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Service
public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
