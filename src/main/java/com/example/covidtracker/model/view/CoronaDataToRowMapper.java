package com.example.covidtracker.model.view;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.model.Province;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CoronaDataToRowMapper {

    private enum CoronaDataRowStyleClass {
        TABLE_DARK("table-secondary"),
        TABLE_LIGHT("table-light");

        private final String styleClass;

        CoronaDataRowStyleClass(String styleClass) {
            this.styleClass = styleClass;
        }

        public String getStyleClass() {
            return this.styleClass;
        }

    }

    private final List<LocationStats> records;

    public List<CoronaDataRow> mapCoronaDataToRow() {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        return this.records
                    .stream()
                    .map(this::convertCoronaDataToRow)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
    }

    private List<CoronaDataRow> convertCoronaDataToRow(LocationStats stat) {
        List<CoronaDataRow> rows = new LinkedList<>();
        if (stat == null) {
            return rows;
        }

        CoronaDataRow row = new CoronaDataRow();
        row.setCountry(stat.getCountry());
        row.setActualCases(stat.getActualCases());
        row.setChangesSinceLastDay(stat.getChangesSinceLastDay());
        row.setState("");
        row.setStyleClass(CoronaDataRowStyleClass.TABLE_DARK.getStyleClass());
        rows.add(row);

        if (!CollectionUtils.isEmpty(stat.getProvinces())) {
            rows.addAll(stat.getProvinces().stream().map(this::convertProvinceToRow).collect(Collectors.toList()));
        }

        return rows;
    }

    private CoronaDataRow convertProvinceToRow(Province p) {
        CoronaDataRow r = new CoronaDataRow();
        r.setCountry("");
        r.setActualCases(p.getLatestToday());
        r.setChangesSinceLastDay(p.getDiffFromPreviousDay());
        r.setState(p.getName());
        r.setStyleClass(CoronaDataRowStyleClass.TABLE_LIGHT.getStyleClass());
        return r;
    }
}
