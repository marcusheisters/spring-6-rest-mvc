package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.mappers.BeerMapper;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName) {
        if (StringUtils.hasText(beerName)) {
            return getBeersByName(beerName)
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .toList();
        }
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }


private List<Beer> getBeersByName(String beerName) {
    return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
}

@Override
public Optional<BeerDTO> getBeerById(UUID id) {
    return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
            .orElse(null)));
}

@Override
public BeerDTO saveNewBeer(BeerDTO beerDTO) {
    return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
}

@Override
public Boolean deleteById(UUID beerId) {
    if (beerRepository.existsById(beerId)) {
        beerRepository.deleteById(beerId);
        return true;
    }
    return false;
}

@Override
public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
    return beerRepository.findById(beerId)
            .map(foundBeer -> {
                foundBeer.setBeerName(beer.getBeerName());
                foundBeer.setBeerStyle(beer.getBeerStyle());
                foundBeer.setPrice(beer.getPrice());
                foundBeer.setUpc(beer.getUpc());
                return Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer)));
            })
            .orElseGet(Optional::empty);
}


@Override
public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
    Optional<Beer> foundBeer = beerRepository.findById(beerId);
    if (foundBeer.isEmpty()) {
        return Optional.empty();
    }

    Beer existing = foundBeer.get();

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
    beerRepository.save(existing);
    return Optional.of(beerMapper.beerToBeerDto(existing));
}
}
