package com.jcosta.xmoney.api.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResourceCreatedListener implements ApplicationListener <ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent event) {
        event.addLocationHeader();
    }
}
