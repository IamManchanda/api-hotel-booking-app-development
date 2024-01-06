package com.harry.iiitb.bookingservice.producers;

import java.io.IOException;

public interface KafkaMessageProducer {
    public void publish(String topic, String key, String value) throws IOException;
}
