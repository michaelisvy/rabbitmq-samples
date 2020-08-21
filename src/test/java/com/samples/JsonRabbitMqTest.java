package com.samples;

import com.samples.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JsonRabbitMqTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void shouldPublishAndReceiveMessage() {
		ZonedDateTime dateOfBirth = ZonedDateTime.of(2008, 7, 31, 0, 0, 0, 0, ZoneId.of("UTC"));
		final Customer customer = new Customer("001", "John", dateOfBirth);

		this.rabbitTemplate.convertAndSend("customerExchange", "jsonRead.#", customer);
		Customer receivedCustomer = (Customer) this.rabbitTemplate.receiveAndConvert("customerJsonReadQueue");
		assertThat(customer).isEqualTo(receivedCustomer);
	}

}
