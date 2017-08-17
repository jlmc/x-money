package com.xcosta.xmoney.api.person.boundary;

import com.xcosta.xmoney.api.event.ResourceCreatedEvent;
import com.xcosta.xmoney.api.person.control.PersonRepository;
import com.xcosta.xmoney.api.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    private final PersonRepository repository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public PersonResource(PersonRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
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
        //Person personByCode = this.repository.getPersonByCode(person.getCode());
        // 409
        //if (personByCode != null) {
        //    return ResponseEntity.status(HttpStatus.CONFLICT).body("code already in use");
        //}

        Person savedPerson = this.repository.save(person);

        this.publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPerson));

//        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
//                .buildAndExpand(savedPerson.getCode()).toUri();
//        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);

    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> buscarPeloCodigo(@PathVariable String code) {
        return Optional.ofNullable(repository.getPersonByCode(code))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable  String code) {
        this.repository.deleteByCode(code);
    }
}
