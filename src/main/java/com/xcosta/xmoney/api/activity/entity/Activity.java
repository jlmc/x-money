package com.xcosta.xmoney.api.activity.entity;

import com.xcosta.xmoney.api.Distinguishable;
import com.xcosta.xmoney.api.category.entity.Category;
import com.xcosta.xmoney.api.person.entity.Person;

import javax.persistence.*;
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
    private String description;
    private String observation;
    private LocalDate payday;
    private LocalDate maturity;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

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
}
