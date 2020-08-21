package com.samples.message;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    LocalDateTime dateTime;
    private boolean hasBeenProcessed;

    private Event() {
    }

    public Event(LocalDateTime dateTime, boolean hasBeenProcessed) {
        this.dateTime = dateTime;
        this.hasBeenProcessed = hasBeenProcessed;
    }

    public String extractTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
