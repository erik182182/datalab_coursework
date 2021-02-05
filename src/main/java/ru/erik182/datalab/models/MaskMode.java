package ru.erik182.datalab.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MaskMode {

    private String name;
    private String regexp;
    private String result;

}
