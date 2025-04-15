package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.BeerCsvDto;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCsvDto> convertCsvToBeerDtos(File file);
}
