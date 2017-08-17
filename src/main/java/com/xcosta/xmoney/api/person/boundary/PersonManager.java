package com.xcosta.xmoney.api.person.boundary;

import com.xcosta.xmoney.api.person.control.PersonRepository;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PersonManager {

    private final PersonRepository repository;

    @Autowired
    PersonManager(PersonRepository repository) {
        this.repository = repository;
    }

    public Person update(String code, Person person) {
        Person savedPerson = this.repository.getPersonByCode(code);

        if (savedPerson == null) {
            throw new EmptyResultDataAccessException(1);
        }

        // Copy properties to the band, instead of making one by one
        BeanUtils.copyProperties(person, savedPerson, "code", "id");

        return this.repository.save(savedPerson);
    }
}
