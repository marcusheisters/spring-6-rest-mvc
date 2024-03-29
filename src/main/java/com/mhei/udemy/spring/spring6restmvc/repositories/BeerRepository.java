package com.mhei.udemy.spring.spring6restmvc.repositories;

import com.mhei.udemy.spring.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author marcusheisters
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
