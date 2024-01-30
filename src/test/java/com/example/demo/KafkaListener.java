package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Lukasz Frankowski
 */
@Component
public class KafkaListener {

    public static final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    private int numberOfRetries = 0;

    @org.springframework.kafka.annotation.KafkaListener(topics = "topic")
    public void onEvent(@NonNull String content) {
        logger.info("Received: {} [{}]", content, numberOfRetries);
        if (numberOfRetries == 0) {
            numberOfRetries++;
            logger.info("Throwing...");
            throw new RuntimeException("Expected");
        }
        logger.info("Continuing...");
        KafkaConfig.latch.countDown();
    }

}
