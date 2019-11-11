package com.samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
	Queue customerJsonReadQueue() {
		return new Queue("customerJsonReadQueue");
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

	@Bean
	Binding bindingJsonReadQueue(Queue customerJsonReadQueue, DirectExchange customerExchange) {
		return BindingBuilder.bind(customerJsonReadQueue).to(customerExchange).with("jsonRead.#");
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(mapper);
	}

}
