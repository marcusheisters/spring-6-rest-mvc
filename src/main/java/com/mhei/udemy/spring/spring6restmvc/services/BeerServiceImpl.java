package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author marcusheisters
 */
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1234432")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine´City")
                .beerStyle(BeerStyle.IPA)
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(44)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);


    }

    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventoryOnHand, Integer pageNumber, Integer pageSize) {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get beer by id was called - in Service");

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        return Optional.of(existing);
    }


    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
        return Optional.empty();
    }
}
