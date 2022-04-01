package com.example.covidtracker.service;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.model.Province;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Data
@RequiredArgsConstructor
class CoronaDataMapper {

    private final Iterable<CSVRecord> records;

    public List<LocationStats> getLocationStats() {
        Map<String, LocationStats> countries = new HashMap<>();
        for (CSVRecord record : this.records) {
            String country = record.get("Country/Region");
            Province p = parseProvince(record);
            if (!StringUtils.isEmpty(country)) {
                if (countries.containsKey(country)) {
                    LocationStats currLocation = countries.get(country);
                    currLocation.addProvince(p);
                    countries.put(country, currLocation);
                } else {
                    LocationStats l = new LocationStats();
                    l.setCountry(country);
                    l.addProvince(p);
                    countries.put(country, l);
                }
            }
        }

        return new LinkedList<>(countries.values());
    }


    private Province parseProvince(CSVRecord record) {
        if (record == null) return null;
        String province = record.get("Province/State");
        int latestCases = Integer.parseInt(record.get(record.size() - 2));
        int todaysCases = Integer.parseInt(record.get(record.size() - 1));

        return new Province(province, todaysCases, latestCases);
    }
}
