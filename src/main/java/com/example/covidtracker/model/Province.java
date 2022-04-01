package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Province {

    private String name;
    private int latestToday;
    private int diffFromPreviousDay;

}
