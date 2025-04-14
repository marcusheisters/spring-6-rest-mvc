package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.BeerCsvDto;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

public class BeerCsvServiceTest {

    BeerCsvServiceImpl beerCsvService = new BeerCsvServiceImpl();

    @Test
    @SneakyThrows
    void convertToCsv() {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCsvDto> recs = beerCsvService.convertCsvToBeerDtos(file);

        System.out.println(recs.size());

        Assertions.assertThat(recs.size()).isGreaterThan(0);

    }
}
