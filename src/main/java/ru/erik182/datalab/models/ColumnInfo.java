package ru.erik182.datalab.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ColumnInfo {

    private String name;
    private String dataType;

}
