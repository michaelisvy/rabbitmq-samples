package com.samples.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "eventQueue"),
            exchange = @Exchange(value = "eventExchange")),
            id = "processEvents"
    )
    public void receiveMessage(Event event) {
        log.info("receiving event {} successfully at {}", event.getId(), event.extractTime());

    }
}
