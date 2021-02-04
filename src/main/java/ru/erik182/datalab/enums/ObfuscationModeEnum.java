package ru.erik182.datalab.enums;

public enum ObfuscationModeEnum {
    MD5("md5"), NULL("null"), DISP("disp");

    private String title;

    ObfuscationModeEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
