package com.example.covidtracker.controllers;

import com.example.covidtracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private CoronaVirusDataService coronaVirusDataService;

    @Autowired
    public HomeController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @GetMapping("/")
    public String home(Model model) {
//        List<LocationStats> stats = this.coronaVirusDataService.getData();
//        long totalReportedCases = stats.stream().mapToLong(LocationStats::getLatestTotal).sum();
//        long totalNewCases = stats.stream().mapToLong(LocationStats::getDiffFromPrevDay).sum();
//        model.addAttribute("locationStats", stats);
//        model.addAttribute("totalReportedCases", totalReportedCases);
//        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }

}
