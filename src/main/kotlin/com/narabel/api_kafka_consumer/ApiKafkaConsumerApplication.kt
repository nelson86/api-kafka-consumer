package com.narabel.api_kafka_consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiKafkaConsumerApplication

fun main(args: Array<String>) {
    runApplication<ApiKafkaConsumerApplication>(*args)
}
