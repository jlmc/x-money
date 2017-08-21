package com.jcosta.xmoney.api.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcosta.xmoney.api.Distinguishable;
import com.jcosta.xmoney.api.category.entity.Category;
import com.jcosta.xmoney.api.person.entity.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "activity")
public class Activity implements Distinguishable <Long> {

    public enum Type {
        RECIPE,
        EXPENSE

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    @NotNull
    private String description;
    @NotNull
    private String observation;
    @NotNull
    private LocalDate payday;
    @NotNull
    private LocalDate maturity;
    @NotNull
    private BigDecimal value;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getCode() {
        return code;
    }

    public String getObservation() {
        return observation;
    }

    public String getDescription() {
        return description;
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

    public Type getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @JsonIgnore
    @XmlTransient
    @Transient
    public String getPersonCode() {
        return this.person.getCode();
    }
}
