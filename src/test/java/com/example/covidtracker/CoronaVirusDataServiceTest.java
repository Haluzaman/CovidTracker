package com.example.covidtracker;


import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.service.CoronaVirusDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoronaVirusDataServiceTest {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @Test
    public void coronaVirusDataServiceTest() {
        List<LocationStats> recordList = coronaVirusDataService.fetchData();
        assert !CollectionUtils.isEmpty(recordList);
        assert recordList.size() == 198;
    }

}
