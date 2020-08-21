package com.samples;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicExchangeConfiguration {

	@Bean
	Queue animalFoxQueue() {
		return new Queue("animalFoxQueue");
	}

	@Bean
	Queue animalBirdQueue() {
		return new Queue("animalBirdQueue");
	}

	@Bean
	Queue animalAllQueue() {
		return new Queue("animalAllQueue");
	}

	@Bean
	TopicExchange animalExchange() {
		return new TopicExchange("animalExchange");
	}

	@Bean
	Binding bindingTopicQueue1AAA(Queue animalFoxQueue, TopicExchange animalExchange) {
		return BindingBuilder.bind(animalFoxQueue).to(animalExchange).with("animal.fox.*");
	}

	@Bean
	Binding bindingTopicQueue2(Queue animalBirdQueue, TopicExchange animalExchange) {
		return BindingBuilder.bind(animalBirdQueue).to(animalExchange).with("animal.bird.*");
	}

	@Bean
	Binding bindingTopicQueue3(Queue animalAllQueue, TopicExchange animalExchange) {
		return BindingBuilder.bind(animalAllQueue).to(animalExchange).with("animal.#");
		/*  `#` is stronger than `*`
			`animal.dog` => ok with `animal.#` and ok with `animal.*`
			`animal.dog.husky` => ok with `animal.#` and NOT OK with `animal.*`
		* */

	}



}
