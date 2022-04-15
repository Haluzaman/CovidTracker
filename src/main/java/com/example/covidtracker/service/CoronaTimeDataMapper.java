package com.example.covidtracker.service;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import lombok.AllArgsConstructor;

import org.apache.commons.csv.CSVRecord;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CoronaTimeDataMapper {

    private final List<CSVRecord> records;

    public List<CoronaTimeData> getCoronaTimeData() {
        List<CoronaTimeData> timeData = new LinkedList<>();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yy");
        List<String> headerNames = records.get(0).getParser().getHeaderNames();
        //only used for aggregating on states that have provinces - fe. Australia
        Map<String, List<CoronaTimeData>> timeDataStateMap = new LinkedHashMap<>();
        for (CSVRecord record : records) {
            String country = record.get("Country/Region");
            String province = record.get("Province/State");
            List<CoronaTimeData> timeDataByProvince = new LinkedList<>();
            //0.  - Province/State
            //1.  - Country/Region
            //2,3 - latitude and Longitude
            //other - dates
            for (int i = 4; i < headerNames.size(); i++) {
                String strDate = headerNames.get(i);
                try {
                    LocalDate date = LocalDate.parse(strDate, formatter1);
                    CoronaTimeData data = new CoronaTimeData();
                    CoronaTimeDataId id = new CoronaTimeDataId(country, province, date);
                    data.setId(id);
                    data.setNumOfCases(Integer.parseInt(record.get(i)));
                    timeDataByProvince.add(data);
                } catch(DateTimeParseException e) {
                    e.printStackTrace();
                }
            }

            if (!StringUtils.isEmpty(province)) {
                List<CoronaTimeData> currentState = timeDataStateMap.get(country);
                if (currentState != null) {
                    currentState.addAll(timeDataByProvince);
                } else {
                    List<CoronaTimeData> data = new LinkedList<>(timeDataByProvince);
                    timeDataStateMap.put(country, data);
                }
            }

            timeData.addAll(timeDataByProvince);
        }

        //Cases in states with provinces must be aggregated together
        for (List<CoronaTimeData> data : timeDataStateMap.values()) {
            Map<String,Map<LocalDate,Integer>> map =
                    data.stream()
                            .collect(Collectors.groupingBy(c -> c.getId().getCountry(),
                                    Collectors.groupingBy(c -> c.getId().getDate(),
                                            Collectors.reducing(0, CoronaTimeData::getNumOfCases, Integer::sum))));

            var list = map.entrySet().stream().map(this::mapEntryToCoronaTimeDataList).flatMap(List::stream).collect(Collectors.toList());
            timeData.addAll(list);
            System.out.println(list);
        }

        return timeData.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<CoronaTimeData> mapEntryToCoronaTimeDataList(Map.Entry<String, Map<LocalDate, Integer>> e) {
        String country = e.getKey();
        Map<LocalDate, Integer> numCasesByDayMap = e.getValue();
        return numCasesByDayMap.entrySet()
                .stream()
                .map(caseByDate -> new CoronaTimeData(new CoronaTimeDataId(country, "", caseByDate.getKey()), caseByDate.getValue()))
                .collect(Collectors.toList());
    }

}
