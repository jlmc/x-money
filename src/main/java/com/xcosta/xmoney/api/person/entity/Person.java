package com.xcosta.xmoney.api.person.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xcosta.xmoney.api.Distinguishable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person implements Distinguishable <String> {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Boolean active;

    @NotNull
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(code, person.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
