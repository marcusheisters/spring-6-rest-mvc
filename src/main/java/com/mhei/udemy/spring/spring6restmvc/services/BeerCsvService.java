package com.mhei.udemy.spring.spring6restmvc.services;

import com.mhei.udemy.spring.spring6restmvc.model.BeerCsvDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public interface BeerCsvService {

    List<BeerCsvDto> convertCsvToBeerDtos(File file);
}
