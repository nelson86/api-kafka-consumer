package com.narabel.api_kafka_consumer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.narabel.api_kafka_consumer.dto.KafkaMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    private val objectMapper: ObjectMapper
) {

    companion object {
        fun consumerProps(kafkaProperties: KafkaProperties): Map<String, Any> {
            return mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId
            )
        }
    }

    @Bean(name = ["config.kafka.consumerFactory"])
    fun consumerFactory(kafkaProperties: KafkaProperties): ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> {
        val configs = consumerProps(kafkaProperties)
        val keyDeserializer = StringDeserializer()
        val valueDeserializer = ErrorHandlingDeserializer<KafkaMessage>(JsonDeserializer(KafkaMessage::class.java, objectMapper))

        val consumerFactory: DefaultKafkaConsumerFactory<String, KafkaMessage> = DefaultKafkaConsumerFactory(
            configs,
            keyDeserializer,
            valueDeserializer
        )

        val containerFactory: ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> = ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>()
        containerFactory.consumerFactory = consumerFactory

        return containerFactory
    }
}
