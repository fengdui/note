package com.fengdui.note.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Date;

/**
 * @author FD
 * @date 2017/10/10
 */
public class Test {
    public static void main(String[] args) {
        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send()
        producer.close();
    }
}
