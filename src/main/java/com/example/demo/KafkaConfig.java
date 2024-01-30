package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.util.backoff.BackOffExecution;

/**
 * @author Lukasz Frankowski
 */
@Configuration
public class KafkaConfig {

    public static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public AfterRollbackProcessor<Object, Object> afterRollbackProcessor(
        KafkaTemplate<?, ?> kafkaTemplate
    ) {
        return new DefaultAfterRollbackProcessor<>(
            (record, exception) -> logger.info("Recovering: {}", record.value(), exception),
            () -> () -> BackOffExecution.STOP,
            kafkaTemplate,
            true
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
        ConcurrentKafkaListenerContainerFactoryConfigurer bootConfigurer,
        ConsumerFactory<Object, Object> consumerFactory,
        AfterRollbackProcessor<Object, Object> afterRollbackProcessor
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();

        // Apply default spring-boot configuration.
        bootConfigurer.configure(factory, consumerFactory);

        factory.setContainerCustomizer(
            container -> {
                logger.info("Customizing: {}", container);
                container.setAfterRollbackProcessor(afterRollbackProcessor);
            });
        return factory;
    }



}
