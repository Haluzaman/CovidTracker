package com.example.covidtracker.controllers;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import com.example.covidtracker.model.TimeData;
import com.example.covidtracker.model.view.DataRangeOption;
import com.example.covidtracker.model.view.DetailTableRow;
import com.example.covidtracker.model.view.DetailTableRowMapper;
import com.example.covidtracker.repository.MongoCoronaTimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
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
                            @RequestParam(value = "selectedRange", required = false) String selectedRange
                            , Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //default value if no selected range is selected
        if (StringUtils.isEmpty(selectedRange)) {
            selectedRange = String.valueOf(365 * 30);
        }

        LocalDate date = LocalDate.now().minusDays(Long.parseLong(selectedRange));

        List<CoronaTimeData> coronaTimeData = mongoCoronaTimeDataRepository.findByFilter(country, province, date ,PageRequest.of(0,100));

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

        List<DataRangeOption> options = new LinkedList<>();
        options.add(new DataRangeOption("All time", 365*30));
        options.add(new DataRangeOption("last week", 7));
        options.add(new DataRangeOption("last month", 30));
        options.add(new DataRangeOption("last 2 months", 60));
        options.add(new DataRangeOption("last 6 months", 120));
        options.add(new DataRangeOption("last year", 365));
        model.addAttribute("dataRangeOptions", options);


        model.addAttribute("region", country + " " + province);
        model.addAttribute("chartData", result);
        model.addAttribute("currentCountry", country);
        model.addAttribute("currentState", province);
        return "detailScreen";
    }

}
