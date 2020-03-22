package com.samples.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

    @Query("from Event where hasBeenProcessed=false")
    List<Event> findUnprocessedEvents();
}
