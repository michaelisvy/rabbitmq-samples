package com.samples;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectExchangeTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void shouldPublishAndReceiveMessage() {
		final String READ_MESSAGE = "customer 1234 - status $1000";
		final String UPDATE_MESSAGE = "customer 1234 - debit $100";

		this.rabbitTemplate.convertAndSend("customerExchange", "read.#", READ_MESSAGE);
		this.rabbitTemplate.convertAndSend("customerExchange", "update.#", UPDATE_MESSAGE);
		String receivedReadMessage = (String) this.rabbitTemplate.receiveAndConvert("customerReadQueue");
		assertThat(READ_MESSAGE).isEqualTo(receivedReadMessage);

		String receivedUpdateMessage = (String) this.rabbitTemplate.receiveAndConvert("customerUpdateQueue");
		assertThat(receivedUpdateMessage).isEqualTo(UPDATE_MESSAGE);
	}

}
