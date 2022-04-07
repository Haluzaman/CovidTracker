package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("CoronaTimeData")
public class CoronaTimeData {

    @Id
    private CoronaTimeDataId id;
    private List<TimeData> dateList = new LinkedList<>();

    public void addTimeData(TimeData data) {
        dateList.add(data);
    }

}
