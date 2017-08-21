package com.jcosta.xmoney.api.activity.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ActivityFilter {

    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate maturityFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate maturityTo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getMaturityFrom() {
        return maturityFrom;
    }

    public void setMaturityFrom(LocalDate maturityFrom) {
        this.maturityFrom = maturityFrom;
    }

    public LocalDate getMaturityTo() {
        return maturityTo;
    }

    public void setMaturityTo(LocalDate maturityTo) {
        this.maturityTo = maturityTo;
    }

}
