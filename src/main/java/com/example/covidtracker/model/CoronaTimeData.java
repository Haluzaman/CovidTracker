package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("CoronaTimeData")
public class CoronaTimeData implements Comparable<CoronaTimeData> {

    @Id
    private CoronaTimeDataId id;
    private int numOfCases;


    @Override
    public int compareTo(CoronaTimeData o) {
        if (o.getId() == null) return -1;
        int a = id.compareTo(o.getId());

        if (a != 0) {
            return a;
        }

        return numOfCases - o.getNumOfCases();
    }
}
