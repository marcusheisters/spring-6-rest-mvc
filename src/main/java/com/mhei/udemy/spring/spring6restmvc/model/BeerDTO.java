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
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotBlank
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
