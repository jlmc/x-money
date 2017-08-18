package com.xcosta.xmoney.api.activity.boundary;

import com.xcosta.xmoney.api.activity.control.ActivityRepository;
import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;
import com.xcosta.xmoney.api.person.control.PersonRepository;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ActivityManager {

    private final ActivityRepository repository;
    private final PersonRepository personRepository;


    @Autowired
    public ActivityManager(ActivityRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    public Page<Activity> search(ActivityFilter activityFilter, Pageable pageable) {
        return repository.search(activityFilter, pageable);
    }

    public Activity findByCode(@PathVariable Long code) {
        Activity activity = repository.findOne(code);

        if (activity == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return activity;
    }

    public Activity create(Activity activity) {

        Person person = this.personRepository.getPersonByCode(activity.getPersonCode());

        if (person == null || !person.isActive()) {
            throw new NonExistentOrInactivePersonException();
        }

        activity.setPerson(person);
        return this.repository.save(activity);
    }

    public void remove(Long code) {
        this.repository.delete(code);
    }
}