package com.jcosta.xmoney.api.event;

import com.jcosta.xmoney.api.Distinguishable;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public class ResourceCreatedEvent extends ApplicationEvent{
    private final HttpServletResponse response;
    private final Distinguishable distinguishable;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, Distinguishable distinguishable) {
        super(source);
        this.response = response;
        this.distinguishable = distinguishable;
    }

    void addLocationHeader() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(this.distinguishable.getCode()).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
