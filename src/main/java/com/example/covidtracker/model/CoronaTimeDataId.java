package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoronaTimeDataId {

    private String country = "";
    private String province = "";

}
