package com.example.covidtracker;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.model.Province;
import com.example.covidtracker.repository.MongoCoronaDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDataServiceTest {

    @Autowired
    private MongoCoronaDataRepository mongoRepository;

    @Test
    public void testSave() {
        LocationStats s = new LocationStats();
        Province p = new Province();
        p.setName("Kosorin 1");
        p.setLatestToday(100);
        p.setDiffFromPreviousDay(10);
        s.setCountry("Kosorin");
        s.setProvinces(Collections.singletonList(p));
        this.mongoRepository.save(s);

        LocationStats k = this.mongoRepository.findByCountry("Kosorin");
        assert k != null;
        assert k.getCountry().equals("Kosorin");
        assert !CollectionUtils.isEmpty(k.getProvinces()) && k.getProvinces().size() == 1;
        assert k.getProvinces().get(0).getName().equals("Kosorin 1");

        this.mongoRepository.delete(s);
    }

}
