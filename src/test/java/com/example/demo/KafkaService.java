package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lukasz Frankowski
 */
@Service
public class KafkaService {

    public static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNonTransactionally() {
        kafkaTemplate.send("topic", "key", "data");
    }

    @Transactional
    public void sendTransactionally() {
        kafkaTemplate.send("topic", "key", "data");
        logger.info("Data sent");
    }

}
