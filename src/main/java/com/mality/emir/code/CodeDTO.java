package com.mality.emir.code;

import com.sun.istack.NotNull;

import javax.validation.constraints.Pattern;

public class CodeDTO {

    private String code;

    // yyyy/MM/dd HH:mm:ss
    @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")
    private String date;

    private Integer time;

    private Integer views;

    public CodeDTO(String code, String date, Integer time, Integer views) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public CodeDTO(String code, String date) {
        this.code = code;
        this.date = date;
    }

    public CodeDTO() {
    }

    public Integer getTime() {
        return Math.max(0, time);
    }

    public void setTime(Integer time) {
        this.time = Math.max(0, time);
    }

    @NotNull
    public Integer getViews() {
        return Math.max(0, views);
    }

    public void setViews(Integer views) {
        this.views = Math.max(0, views);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
