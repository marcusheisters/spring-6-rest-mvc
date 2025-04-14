package com.mhei.udemy.spring.spring6restmvc.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerCsvDto{

        @CsvBindByName
        Integer row;

        @CsvBindByName(column = "count.x")
        Integer count;

        @CsvBindByName
        String abv;

        @CsvBindByName
        String ibu;

        @CsvBindByName
        Integer id;

        @CsvBindByName
        String beer;

        @CsvBindByName
        String style;

        @CsvBindByName(column = "brewery_id")
        Integer breweryId;

        @CsvBindByName
        Float ounces;

        @CsvBindByName
        String style2;

        @CsvBindByName(column = "count.y")
        String count_y;

        @CsvBindByName
        String brewery;

        @CsvBindByName
        String city;

        @CsvBindByName
        String state;

        @CsvBindByName
        String label;

}
