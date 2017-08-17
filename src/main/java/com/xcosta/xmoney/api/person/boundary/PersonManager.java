package com.xcosta.xmoney.api.person.boundary;

import com.xcosta.xmoney.api.person.control.PersonRepository;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonManager {

    private final PersonRepository repository;

    @Autowired
    PersonManager(PersonRepository repository) {
        this.repository = repository;
    }

    public Person update(String code, Person person) {
        Person savedPerson = getPerson(code);

        // Copy properties to the band, instead of making one by one
        BeanUtils.copyProperties(person, savedPerson, "code", "id");

        return this.repository.save(savedPerson);
    }



    public Person changeStatus(String code, Boolean status) {
        Person savedPerson = this.getPerson(code);

        savedPerson.setActive(status);

        return this.repository.save(savedPerson);
    }

    private Person getPerson(String code) {
        Person savedPerson = this.repository.getPersonByCode(code);

        if (savedPerson == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return savedPerson;
    }

    public List<Person> search() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    public Person create(Person person) {
        return this.repository.save(person);
    }

    public Person findByCode(String code) {
        return this.getPerson(code);
    }

    public void delete(String code) {
        this.repository.deleteByCode(code);
    }
}
