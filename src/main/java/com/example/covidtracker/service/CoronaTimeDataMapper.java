package com.example.covidtracker.service;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import com.example.covidtracker.model.TimeData;
import lombok.AllArgsConstructor;

import org.apache.commons.csv.CSVRecord;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CoronaTimeDataMapper {

    private final List<CSVRecord> records;

    public List<CoronaTimeData> getCoronaTimeData() {
        List<CoronaTimeData> timeData = new LinkedList<>();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yy");
        List<String> headerNames = records.get(0).getParser().getHeaderNames();
        for (CSVRecord record : records) {
            String country = record.get("Country/Region");
            String province = record.get("Province/State");
            CoronaTimeData data = new CoronaTimeData();
            CoronaTimeDataId dataId = new CoronaTimeDataId(country, province);
            data.setId(dataId);
            //0. - Province/State
            //1. -Country/Region
            //2,3 - latitude and Longitude
            //other - dates
            for (int i = 4; i < headerNames.size(); i++) {
                String strDate = headerNames.get(i);
                try {
                    LocalDate date = LocalDate.parse(strDate, formatter1);
                    data.addTimeData(new TimeData(date, Integer.parseInt(record.get(i))));
                } catch(DateTimeParseException e) {
                    e.printStackTrace();
                }
            }

            timeData.add(data);
        }

        return timeData;
    }

}
