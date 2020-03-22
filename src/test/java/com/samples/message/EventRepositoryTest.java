package com.samples.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldPersistEvent() {
        Event event = new Event(LocalDateTime.now(), false);
        Assertions.assertThat(event.getId()).isZero();
        this.eventRepository.save(event);
        Assertions.assertThat(event.getId()).isNotZero();
    }

}
