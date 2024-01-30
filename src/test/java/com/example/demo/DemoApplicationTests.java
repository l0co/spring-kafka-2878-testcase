package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoApplicationTests {

    @Autowired private KafkaService kafkaService;

	@Test
	void shouldBeTransactional() {
        assertThrows(IllegalStateException.class, () -> kafkaService.sendNonTransactionally());
        assertDoesNotThrow(() -> kafkaService.sendTransactionally());
	}

}
