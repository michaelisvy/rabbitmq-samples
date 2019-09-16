package com.samples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutExchangeTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void shouldPublishAndReceiveMessage() {
		final String MESSAGE = "customer 1234 - status $1000";

		this.rabbitTemplate.convertAndSend("fanoutExchange", "", MESSAGE);

		String receivedMessage1 = (String) this.rabbitTemplate.receiveAndConvert("fanoutQueue1");
		assertThat(MESSAGE).isEqualTo(receivedMessage1);

		String receivedMessage2 = (String) this.rabbitTemplate.receiveAndConvert("fanoutQueue2");
		assertThat(MESSAGE).isEqualTo(receivedMessage2);

		String receivedMessage3 = (String) this.rabbitTemplate.receiveAndConvert("fanoutQueue3");
		assertThat(MESSAGE).isEqualTo(receivedMessage3);
	}

}
