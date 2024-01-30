package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author Lukasz Frankowski
 */
@Component
public class KafkaListener {

    public static final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    public CountDownLatch latch = new CountDownLatch(1);

    @org.springframework.kafka.annotation.KafkaListener(topics = "topic")
    public void onEvent(@NonNull String content) {
        logger.info("Received: {}", content);
        latch.countDown();
    }

}
