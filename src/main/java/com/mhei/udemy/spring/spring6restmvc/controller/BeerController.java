package com.mhei.udemy.spring.spring6restmvc.controller;

import com.mhei.udemy.spring.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * @author marcusheisters
 */
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;
}
