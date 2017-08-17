package com.xcosta.xmoney.api.category.entity;

import com.xcosta.xmoney.api.Distinguishable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category implements Distinguishable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @Override
    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(this.name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }
}
