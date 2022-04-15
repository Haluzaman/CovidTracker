package com.example.covidtracker.repository;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoCoronaTimeDataRepository extends MongoRepository<CoronaTimeData, CoronaTimeDataId> {

    @Query(value = "{ " +
            "'_id.country' : ?0," +
            " '_id.province' : ?1 }",
         sort = "{ '_id.date' : -1 }")
    List<CoronaTimeData> findByFilter(String country, String province);

    @Query(value = "{ " +
            "'_id.country' : ?0," +
            " '_id.province' : ?1 }",
            sort = "{ '_id.date' : -1 }")
    List<CoronaTimeData> findByFilter(String country, String province, Pageable page);

}
