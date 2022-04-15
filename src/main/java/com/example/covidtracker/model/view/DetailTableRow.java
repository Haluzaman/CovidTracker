package com.example.covidtracker.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailTableRow {

    private String country;
    private String province;
    private int numberOfCases;
    private LocalDate date;

}
