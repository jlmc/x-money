package com.xcosta.xmoney.api.activity.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ActivitySummary {

    private Long code;
    private String description;
    private String observation;
    private LocalDate payday;
    private LocalDate maturity;
    private BigDecimal value;
    private Activity.Type type;
    private String category;
    private String person;

    public ActivitySummary(Long code, String description, String observation, LocalDate payday,
                           LocalDate maturity, BigDecimal value, Activity.Type type,
                           String category, String person) {
        this.code = code;
        this.description = description;
        this.observation = observation;
        this.payday = payday;
        this.maturity = maturity;
        this.value = value;
        this.type = type;
        this.category = category;
        this.person = person;
    }

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getObservation() {
        return observation;
    }

    public LocalDate getPayday() {
        return payday;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Activity.Type getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getPerson() {
        return person;
    }
}
