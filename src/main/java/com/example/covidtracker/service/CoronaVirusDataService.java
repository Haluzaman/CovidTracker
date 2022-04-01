package com.example.covidtracker.service;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.repository.MongoCoronaDataRepository;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@Service
public class CoronaVirusDataService {

    private static final String VIRUS_DATA_GITHUB_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private final MongoCoronaDataRepository mongoCoronaDataRepository;

    public CoronaVirusDataService(@Autowired MongoCoronaDataRepository mongoDataService) {
        this.mongoCoronaDataRepository = mongoDataService;
    }

    //8 o'clock everyday
    @Scheduled(cron = "0 0 8 * * *")
    public void fetchAndSaveData() {
        List<LocationStats> stats = fetchData();
        this.mongoCoronaDataRepository.saveAll(stats);
    }

    public  List<LocationStats> fetchData() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_GITHUB_URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            StringReader csvBodyReader = new StringReader(response.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
            CoronaDataMapper mapper = new CoronaDataMapper(records);

            return mapper.getLocationStats();
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
