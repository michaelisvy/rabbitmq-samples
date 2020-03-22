package com.samples.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @Slf4j
public class EventPublisher {
    public static final int FIVE_SECONDS = 5000;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = FIVE_SECONDS)
    public void publishEvents() {
        List<Event> events = this.eventRepository.findUnprocessedEvents();

        for(Event event: events) {
            log.info("posting event {}", event.getTime());
            this.rabbitTemplate.convertAndSend("eventExchange", "event.#", event);
            event.setHasBeenProcessed(true);
            this.eventRepository.save(event);
        }
    }
}
