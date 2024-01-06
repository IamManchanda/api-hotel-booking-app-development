package com.harry.iiitb.notificationservice.consumers;

import java.time.Duration;
import java.util.*;
import org.apache.kafka.clients.consumer.*;

public class KafkaMessageConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "ec2-user@ec2-52-7-133-91.compute-1.amazonaws.com:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("message"));

        Set<String> subscribedTopics = consumer.subscription();
        subscribedTopics.stream().forEach(System.out::println);

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                // Start consuming messages in a forever loop
                // for notification service using Kafka.
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("message = %s%n", record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
