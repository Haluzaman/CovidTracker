package com.example.covidtracker.repository;

import com.example.covidtracker.model.LocationStats;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoCoronaDataRepository extends MongoRepository<LocationStats, String> {

    boolean existsByCountry(String country);

    List<LocationStats> findAllByCountryIn(List<String> countryList);

    LocationStats findByCountry(String country);

}
