package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CoronaTimeDataId implements Comparable<CoronaTimeDataId> {

    private String country = "";
    private String province = "";
    private LocalDate date;

    @Override
    public int compareTo(CoronaTimeDataId o) {
        String x1 = o.getCountry();
        int sComp = this.country.compareTo(x1);

        if (sComp != 0) {
            return sComp;
        }

        String x2 = o.getProvince();
        sComp = this.province.compareTo(x2);
        if (sComp != 0) {
            return sComp;
        }

        LocalDate x3 = o.getDate();
        return this.getDate().compareTo(x3);
    }

}
