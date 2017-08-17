package com.xcosta.xmoney.api.activity.boundary;

import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.event.ResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityResource {

    private final ActivityManager manager;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ActivityResource(ActivityManager manager, ApplicationEventPublisher publisher) {
        this.manager = manager;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Activity> search() {
        return this.manager.search();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Activity> findByCode(@PathVariable Long code) {
        Activity activity = manager.findByCode(code);
        return activity != null ? ResponseEntity.ok(activity) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Activity> create(@Valid @RequestBody Activity activity,
                                           HttpServletResponse response) {

        Activity savedActivity = this.manager.create(activity);
        //Activity lancamentoSalvo = lancamentoRepository.save(activity);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, savedActivity));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

}
