package com.example.covidtracker.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DataRangeOption {

    private String text;
    private int days;

}
