package com.example.covidtracker.model.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoronaDataRow {

    private String country;
    private String state;
    private int actualCases;
    private int changesSinceLastDay;
    private String styleClass;

}
