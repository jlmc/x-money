package com.xcosta.xmoney.api.activity.boundary;

import static com.xcosta.xmoney.api.XMoneyExceptionHandler.*;

import com.xcosta.xmoney.api.XMoneyExceptionHandler;
import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;
import com.xcosta.xmoney.api.event.ResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

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
    public List<Activity> search(ActivityFilter activityFilter) {
        return this.manager.search(activityFilter);
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

    @ExceptionHandler({NonExistentOrInactivePersonException.class})
    public ResponseEntity<Object> handlerNonExistentOrInactivePersonException(NonExistentOrInactivePersonException e) {
        String userMessage = messageSource.getMessage(e.getMessageKey(), new Object[0], LocaleContextHolder.getLocale());
        String devMessage = e.getCause() != null ? e.getCause().toString() : e.toString();

        return ResponseEntity.badRequest().body(Collections.singletonList(XMoneyExceptionHandler.Error.of(userMessage, devMessage)));
    }

}
