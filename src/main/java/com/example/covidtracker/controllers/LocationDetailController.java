package com.example.covidtracker.controllers;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import com.example.covidtracker.model.TimeData;
import com.example.covidtracker.repository.MongoCoronaTimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/detail")
public class LocationDetailController {

    private final MongoCoronaTimeDataRepository mongoCoronaTimeDataRepository;

    public LocationDetailController(@Autowired MongoCoronaTimeDataRepository mongoCoronaTimeDataRepository) {
        this.mongoCoronaTimeDataRepository = mongoCoronaTimeDataRepository;
    }

    @GetMapping
    public String getDetail(@RequestParam(value = "country") String country,
                            @RequestParam(value = "province") String province,
                            Model model) {
        System.out.println("Requesting: " + country + " " + province);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<CoronaTimeData> coronaTimeData = mongoCoronaTimeDataRepository.findByFilter(country, province);
        List<TimeData> timeData = coronaTimeData.stream().map(CoronaTimeData::getDateList).flatMap(List::stream).collect(Collectors.toList());
        Object[][] result = new Object[timeData.size()][2];
        for (int i = 0; i < timeData.size(); i++) {
            TimeData data = timeData.get(i);
            result[i][0] = data.getDate().format(formatter);
            result[i][1] = data.getNumOfCases();
        }

        model.addAttribute("region", country + " " + province);
        model.addAttribute("chartData", result);
        return "detailScreen";
    }

}
