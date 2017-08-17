package com.xcosta.xmoney.api.person.control;

import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person getPersonByCode(String code);
}
