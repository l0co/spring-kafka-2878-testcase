package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

    @Autowired private KafkaService kafkaService;

	@Test
	void shouldBeTransactional() {
        assertThrows(IllegalStateException.class, () -> kafkaService.sendNonTransactionally());
	}

	@Test
	void shouldProcessEvent() throws InterruptedException {
        kafkaService.sendTransactionally();
        assertTrue(KafkaConfig.latch.await(30, TimeUnit.SECONDS));
	}

}
