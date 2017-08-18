package com.xcosta.xmoney.api.person.boundary;

import com.xcosta.xmoney.api.event.ResourceCreatedEvent;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    private final ApplicationEventPublisher publisher;
    private final PersonManager manager;

    @Autowired
    public PersonResource(PersonManager manager, ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        this.manager = manager;
    }

    @GetMapping("/echo")
    public ResponseEntity<?> echo() {
        return ResponseEntity.ok("persons - " + System.currentTimeMillis());
    }


    @GetMapping
    public List<Person> list() {
        return this.manager.search();
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid Person person, HttpServletResponse response) {
        Person savedPerson = this.manager.create(person);

        this.publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPerson));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);

    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(this.manager.findByCode(code)) ;
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable  String code) {
        this.manager.delete(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Person> update(@Valid @RequestBody Person person, @PathVariable String code) {
        Person updated = this.manager.update(code, person);
        return ResponseEntity.ok(updated);

    }

    @PutMapping("/{code}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable String code, @RequestBody Boolean status) {
        this.manager.changeStatus(code, status);
    }
}
