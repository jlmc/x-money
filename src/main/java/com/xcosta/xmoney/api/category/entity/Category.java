package com.xcosta.xmoney.api.category.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String name;

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
