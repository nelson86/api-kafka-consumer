package com.narabel.api_kafka_consumer.dto

data class KafkaMessage(
    val action: String,
    val data: Any
)
