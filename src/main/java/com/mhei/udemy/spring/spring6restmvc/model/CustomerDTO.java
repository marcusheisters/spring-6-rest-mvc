package com.mhei.udemy.spring.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author marcusheisters
 */
@Data
@Builder
public class CustomerDTO {
    private UUID id;
    private Integer version;
    private String name;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
