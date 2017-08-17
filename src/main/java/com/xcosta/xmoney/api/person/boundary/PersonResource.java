package com.xcosta.xmoney.api.person.boundary;

import com.xcosta.xmoney.api.person.control.PersonRepository;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    private final PersonRepository repository;

    @Autowired
    public PersonResource(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/echo")
    public ResponseEntity<?> echo() {
        return ResponseEntity.ok("persons - " + System.currentTimeMillis());
    }


    @GetMapping
    public List<Person> list() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid Person person, HttpServletResponse response) {

        Person personByCode = this.repository.getPersonByCode(person.getCode());

        // 409
        if (personByCode != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("code already in use");
        }

        Person savedPerson = this.repository.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(savedPerson.getCode()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(savedPerson);

    }
}
