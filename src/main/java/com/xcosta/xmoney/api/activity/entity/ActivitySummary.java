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

    private String categoryName;
    private String personName;

    public ActivitySummary() {}

    public ActivitySummary(
            Long code, String description, String observation,
            LocalDate payday, LocalDate maturity,
            BigDecimal value, Activity.Type type,
            String categoryName, String personName) {
        this.code = code;
        this.description = description;
        this.observation = observation;
        this.payday = payday;
        this.maturity = maturity;
        this.value = value;
        this.type = type;
        this.categoryName = categoryName;
        this.personName = personName;
    }


    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getPayday() {
        return payday;
    }

    public void setPayday(LocalDate payday) {
        this.payday = payday;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public void setMaturity(LocalDate maturity) {
        this.maturity = maturity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Activity.Type getType() {
        return type;
    }

    public void setType(Activity.Type type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
