package com.example.covidtracker.model.view;

import com.example.covidtracker.model.CoronaTimeData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class DetailTableRowMapper {

    private final List<CoronaTimeData> data;

    public List<DetailTableRow> getDetailTableRowData() {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data
                .stream()
                .map(d -> new DetailTableRow(d.getId().getCountry(), d.getId().getProvince(), d.getNumOfCases(), d.getId().getDate()))
                .collect(Collectors.toList());
    }

}
