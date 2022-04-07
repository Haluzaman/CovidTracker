package com.example.covidtracker.service;

import com.example.covidtracker.model.CoronaTimeData;
import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.repository.MongoCoronaDataRepository;
import com.example.covidtracker.repository.MongoCoronaTimeDataRepository;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
    private final MongoCoronaTimeDataRepository mongoCoronaTimeDataRepository;

    public CoronaVirusDataService(@Autowired MongoCoronaDataRepository mongoDataService,
                                  @Autowired MongoCoronaTimeDataRepository mongoCoronaTimeDataRepository) {
        this.mongoCoronaDataRepository = mongoDataService;
        this.mongoCoronaTimeDataRepository = mongoCoronaTimeDataRepository;
    }

    //8 o'clock everyday
    @Scheduled(cron = "0 0 8 * * *")
//    @PostConstruct
    public void fetchAndSaveData() {
        List<LocationStats> stats = fetchData();
        List<CoronaTimeData> timeData = fetchTimeData();
        this.mongoCoronaDataRepository.saveAll(stats);
        this.mongoCoronaTimeDataRepository.saveAll(timeData);
    }

    private List<CSVRecord> fetchRawData() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_GITHUB_URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            StringReader csvBodyReader = new StringReader(response.body());
            CSVParser parser = CSVParser.parse(csvBodyReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            return parser.getRecords();
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<LocationStats> fetchData() {
        List<CSVRecord> records = fetchRawData();
        CoronaDataMapper mapper = new CoronaDataMapper(records);

        return mapper.getLocationStats();
    }

    public List<CoronaTimeData> fetchTimeData() {
        List<CSVRecord> records = fetchRawData();
        CoronaTimeDataMapper mapper = new CoronaTimeDataMapper(records);

        return mapper.getCoronaTimeData();
    }

}
