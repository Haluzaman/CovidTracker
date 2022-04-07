package com.example.covidtracker.repository;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.CoronaTimeDataId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoCoronaTimeDataRepository extends MongoRepository<CoronaTimeData, CoronaTimeDataId> {

    @Query("{ " +
            "'_id.country' : /?0/," +
            " '_id.province' : /?1/" +
            ", $or : [" +
                    "{ '_id.country' : /?0/}," +
                    " { '_id.province' : /?1/ }" +
                "] " +
            "}")
    List<CoronaTimeData> findByFilter(String country, String province);

}
