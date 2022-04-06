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

            //there is only one province in the Country
            if (StringUtils.isEmpty(p.getName())) {
                LocationStats l = new LocationStats();
                l.setCountry(country);
                l.setActualCases(p.getLatestToday());
                l.setChangesSinceLastDay(p.getDiffFromPreviousDay());
                countries.put(country, l);
            } else {
                LocationStats currLocation = countries.get(country);
                if (currLocation != null) {
                    currLocation.addActualCases(p.getLatestToday());
                    currLocation.addChangesSinceLastDay(p.getDiffFromPreviousDay());
                    currLocation.addProvince(p);
                    countries.put(country, currLocation);
                } else {
                    LocationStats l = new LocationStats();
                    l.setCountry(country);
                    l.addProvince(p);
                    l.addActualCases(p.getLatestToday());
                    l.addChangesSinceLastDay(p.getDiffFromPreviousDay());
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

        return new Province(province, todaysCases, todaysCases - latestCases);
    }
}
