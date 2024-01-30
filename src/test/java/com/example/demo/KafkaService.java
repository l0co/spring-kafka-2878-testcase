package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lukasz Frankowski
 */
@Service
public class KafkaService {

    @Autowired private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNonTransactionally() {
        kafkaTemplate.send("topic", "key", "data");
    }

    @Transactional
    public void sendTransactionally() {
        kafkaTemplate.send("topic", "key", "data");
    }

}
