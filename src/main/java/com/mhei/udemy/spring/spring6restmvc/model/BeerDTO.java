package com.mhei.udemy.spring.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Data
@Builder
public class BeerDTO {

    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
