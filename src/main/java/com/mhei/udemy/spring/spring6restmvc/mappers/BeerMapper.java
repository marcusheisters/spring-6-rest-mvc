package com.mhei.udemy.spring.spring6restmvc.mappers;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

/**
 * @author marcusheisters
 */
@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
