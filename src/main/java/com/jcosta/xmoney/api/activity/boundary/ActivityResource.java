package com.jcosta.xmoney.api.activity.boundary;

import com.jcosta.xmoney.api.activity.entity.Activity;
import com.jcosta.xmoney.api.activity.entity.ActivityFilter;
import com.jcosta.xmoney.api.event.ResourceCreatedEvent;
import com.jcosta.xmoney.api.XMoneyExceptionHandler;
import com.jcosta.xmoney.api.activity.entity.ActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/activities")
public class ActivityResource {

    private final ActivityManager manager;
    private final ApplicationEventPublisher publisher;
    private final MessageSource messageSource;

    @Autowired
    public ActivityResource(ActivityManager manager,
                            ApplicationEventPublisher publisher,
                            MessageSource messageSource) {
        this.manager = manager;
        this.publisher = publisher;
        this.messageSource = messageSource;
    }

    @GetMapping
    public Page<Activity> search(ActivityFilter activityFilter, Pageable pageable) {
        return this.manager.search(activityFilter, pageable);
    }

    @GetMapping("/summary")
    public Page<ActivitySummary> searchSummary(ActivityFilter activityFilter, Pageable pageable) {
        return this.manager.searchSummary(activityFilter, pageable);
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

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable  Long code) {
        this.manager.remove(code);
    }

    @ExceptionHandler({NonExistentOrInactivePersonException.class})
    public ResponseEntity<Object> handlerNonExistentOrInactivePersonException(NonExistentOrInactivePersonException e) {
        String userMessage = messageSource.getMessage(e.getMessageKey(), new Object[0], LocaleContextHolder.getLocale());
        String devMessage = e.getCause() != null ? e.getCause().toString() : e.toString();

        return ResponseEntity.badRequest().body(Collections.singletonList(XMoneyExceptionHandler.Error.of(userMessage, devMessage)));
    }

}
