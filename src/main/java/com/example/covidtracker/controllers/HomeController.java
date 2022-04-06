package com.example.covidtracker.controllers;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.model.view.CoronaDataRow;
import com.example.covidtracker.model.view.CoronaDataToRowMapper;
import com.example.covidtracker.repository.MongoCoronaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private final MongoCoronaDataRepository coronaDataRepository;

    @Autowired
    public HomeController(MongoCoronaDataRepository coronaDataRepository) {
        this.coronaDataRepository = coronaDataRepository;
    }

    @GetMapping("/")
    public String home(Model model, String filterBy) {
        List<LocationStats> stats = null;
        if (!StringUtils.isEmpty(filterBy)) {
            stats = this.coronaDataRepository.getByFilter(filterBy);
        } else {
            stats = this.coronaDataRepository.findAllByOrderByCountry();
        }
//        List<LocationStats> stats = this.coronaDataRepository.findAll();
        CoronaDataToRowMapper mapper = new CoronaDataToRowMapper(stats);
        List<CoronaDataRow> rows = mapper.mapCoronaDataToRow();
        int totalReportedCases = stats.stream().mapToInt(LocationStats::getActualCases).sum();
        int totalNewCases = stats.stream().mapToInt(LocationStats::getChangesSinceLastDay).sum();

        model.addAttribute("coronaDataRow", rows);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }



}
