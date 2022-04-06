package com.example.covidtracker.repository;

import com.example.covidtracker.model.LocationStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoCoronaDataRepository extends MongoRepository<LocationStats, String> {

    boolean existsByCountry(String country);

    List<LocationStats> findAllByCountryIn(List<String> countryList);

    LocationStats findByCountry(String country);

    @Query(value = "{country: /?0/}", sort = "{country:1}")
    List<LocationStats> getByFilter(String filterBy);

}
