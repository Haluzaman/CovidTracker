package com.example.covidtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
public class CovidTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerApplication.class, args);
        System.out.println("***************************************\n");
        System.out.println("http://localhost:8080/");
        System.out.println("***************************************\n");
    }

}
