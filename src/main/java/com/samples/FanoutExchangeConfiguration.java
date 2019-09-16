package com.samples;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutExchangeConfiguration {

	@Bean
	Queue queue1() {
		return new Queue("fanoutQueue1");
	}

	@Bean
	Queue queue2() {
		return new Queue("fanoutQueue2");
	}

	@Bean
	Queue queue3() {
		return new Queue("fanoutQueue3");
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}

	@Bean
	Binding bindingQueue1(Queue queue1, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue1).to(fanoutExchange);
	}

	@Bean
	Binding bindingQueue2(Queue queue2, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue2).to(fanoutExchange);
	}

	@Bean
	Binding bindingQueue3(Queue queue3, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue3).to(fanoutExchange);
	}



}
