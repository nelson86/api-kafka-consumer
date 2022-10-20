package com.narabel.api_kafka_consumer.consumer

import com.narabel.api_kafka_consumer.dto.KafkaMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CreatedUserConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["users.v1.created"],
        containerFactory = "config.kafka.consumerFactory"
    )
    fun listen(message: KafkaMessage) {
        // call UseCase
        log.info("Message: $message")
    }
}
