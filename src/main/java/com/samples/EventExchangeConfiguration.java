package com.samples;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EventExchangeConfiguration {

	@Bean
	Queue eventQueue() {
		return new Queue("eventQueue");
	}

	@Bean
	DirectExchange eventExchange() {
		return new DirectExchange("eventExchange");
	}

	@Bean
	Binding bindingEventQueue(Queue eventQueue, DirectExchange eventExchange) {
		return BindingBuilder.bind(eventQueue).to(eventExchange).with("event.#");
	}

}
