package com.example.covidtracker.controllers;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import com.example.covidtracker.model.TimeData;
import com.example.covidtracker.model.view.DetailTableRow;
import com.example.covidtracker.model.view.DetailTableRowMapper;
import com.example.covidtracker.repository.MongoCoronaTimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<CoronaTimeData> coronaTimeData = mongoCoronaTimeDataRepository.findByFilter(country, province, PageRequest.of(0,100));

        DetailTableRowMapper mapper = new DetailTableRowMapper(coronaTimeData);
        List<DetailTableRow> tableRows = mapper.getDetailTableRowData();
        model.addAttribute("tableData", tableRows);

        Collections.reverse(coronaTimeData);

        Object[][] result = new Object[coronaTimeData.size()][2];
        for (int i = 0; i < coronaTimeData.size(); i++) {
            CoronaTimeData data = coronaTimeData.get(i);
            result[i][0] = data.getId().getDate().format(formatter);
            result[i][1] = data.getNumOfCases();
        }



        model.addAttribute("region", country + " " + province);
        model.addAttribute("chartData", result);
        return "detailScreen";
    }

}
