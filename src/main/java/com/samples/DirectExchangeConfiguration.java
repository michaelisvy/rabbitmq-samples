package com.samples;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DirectExchangeConfiguration {

	@Bean
	Queue customerReadQueue() {
		return new Queue("customerReadQueue");
	}

	@Bean
	Queue customerUpdateQueue() {
		return new Queue("customerUpdateQueue");
	}

	@Bean
	DirectExchange customerExchange() {
		return new DirectExchange("customerExchange");
	}

	@Bean
	Binding bindingReadQueue(Queue customerReadQueue, DirectExchange customerExchange) {
		return BindingBuilder.bind(customerReadQueue).to(customerExchange).with("read.#");
	}

	@Bean
	Binding bindingUpdateQueue(Queue customerUpdateQueue, DirectExchange customerExchange) {
		return BindingBuilder.bind(customerUpdateQueue).to(customerExchange).with("update.#");
	}

}
