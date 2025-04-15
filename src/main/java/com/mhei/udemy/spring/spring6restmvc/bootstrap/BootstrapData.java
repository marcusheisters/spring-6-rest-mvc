package com.mhei.udemy.spring.spring6restmvc.bootstrap;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import com.mhei.udemy.spring.spring6restmvc.entities.Customer;
import com.mhei.udemy.spring.spring6restmvc.model.BeerCsvDto;
import com.mhei.udemy.spring.spring6restmvc.model.BeerStyle;
import com.mhei.udemy.spring.spring6restmvc.repositories.BeerRepository;
import com.mhei.udemy.spring.spring6restmvc.repositories.CustomerRepository;
import com.mhei.udemy.spring.spring6restmvc.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCsvDto> beerCsvDtos = beerCsvService.convertCsvToBeerDtos(file);

            beerCsvDtos.forEach(record -> {
                BeerStyle beerStyle = switch (record.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale",
                         "American " +
                                 "Blonde Ale" -> BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" ->
                            BeerStyle.IPA;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Season / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" ->
                            BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(record.getBeer())
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(record.getRow().toString())
                        .quantityOnHand(record.getCount())
                        .build());
            });
        }
    }

    private void loadCustomerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("1234432")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine´City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12345612")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(44)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }
    }

    private void loadBeerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Bill Edwards")
                    .version(1)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Tiff Taylor")
                    .version(1)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Parka Pete")
                    .version(1)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }

}
