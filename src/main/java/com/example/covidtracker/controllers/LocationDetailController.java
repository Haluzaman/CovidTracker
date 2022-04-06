package com.example.covidtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/detail")
public class LocationDetailController {

    @GetMapping
    public String getDetail(@RequestParam(value = "country") String country,
                            @RequestParam(value = "state") String state,
                            Model model) {
        System.out.println("Requesting: " + country + " " + state);
        return "detailScreen";
    }

}
