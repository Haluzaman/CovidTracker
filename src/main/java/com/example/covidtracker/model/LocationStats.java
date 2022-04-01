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
@Document("data")
public class LocationStats {

    @Id
    private String country;
    private List<Province> provinces = new LinkedList<>();

    public void addProvince(Province p) {
        this.provinces.add(p);
    }

}
