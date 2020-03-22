package com.samples.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final String FIVE_SECONDS = "5000";

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        persistEvent();
    }

    @Transactional
    public void persistEvent() {
        Event event = new Event(LocalDateTime.now(), false);
        this.eventRepository.save(event);
        log.info("created event in DB: {}" + event.getTime());
    }
}
