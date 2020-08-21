package com.samples;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TopicExchangeTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	public static final String MESSAGE = "Hi Animal!";

	@Test
	public void messageShouldOnlyBeSentToSelectedQueue() {

		this.rabbitTemplate.convertAndSend("animalExchange", "animal.bird.hummingbird", MESSAGE);

		String receivedMessage2 = (String) this.rabbitTemplate.receiveAndConvert("animalBirdQueue");
		assertThat(receivedMessage2).isEqualTo(MESSAGE);

		String receivedMessage1 = (String) this.rabbitTemplate.receiveAndConvert("animalFoxQueue");
		assertThat(receivedMessage1).isNull();

		String receivedMessage3 = (String) this.rabbitTemplate.receiveAndConvert("animalAllQueue");
		assertThat(receivedMessage3).isEqualTo(MESSAGE);
	}

	@Test
	public void unknownAnimalShouldGoToDefaultQueue() {

		this.rabbitTemplate.convertAndSend("animalExchange", "animal.honeyBadger", MESSAGE);

		String receivedMessage2 = (String) this.rabbitTemplate.receiveAndConvert("animalFoxQueue");
		assertThat(receivedMessage2).isNull();

		String receivedMessage1 = (String) this.rabbitTemplate.receiveAndConvert("animalBirdQueue");
		assertThat(receivedMessage1).isNull();

		String receivedMessage3 = (String) this.rabbitTemplate.receiveAndConvert("animalAllQueue");
		assertThat(receivedMessage3).isEqualTo(MESSAGE);
	}

	@Test
	public void unspecifiedAnimalShouldGoToDefaultQueue() {

		this.rabbitTemplate.convertAndSend("animalExchange", "animal", MESSAGE);

		String receivedMessage2 = (String) this.rabbitTemplate.receiveAndConvert("animalFoxQueue");
		assertThat(receivedMessage2).isNull();

		String receivedMessage1 = (String) this.rabbitTemplate.receiveAndConvert("animalBirdQueue");
		assertThat(receivedMessage1).isNull();

		String receivedMessage3 = (String) this.rabbitTemplate.receiveAndConvert("animalAllQueue");
		assertThat(receivedMessage3).isEqualTo(MESSAGE);
	}

}
