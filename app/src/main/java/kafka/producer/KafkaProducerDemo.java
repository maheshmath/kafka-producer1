package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import java.util.Properties;

public class KafkaProducerDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.7:9092,192.168.1.5:9092,192.168.1.3:9092"); // Kafka broker(s)
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create the producer
        Producer<String, String> producer = new KafkaProducer<>(props);
        // Send a record to the topic "sample-topic"
        Future<RecordMetadata> future = producer.send(new ProducerRecord<>("sample-topic", "Val", "100"));

        // Optionally wait for the send to complete
        RecordMetadata metadata = future.get();
        System.out.println("Sent message with key: Val and value: 100"  + 
                            " to partition: " + metadata.partition() + 
                            " with offset: " + metadata.offset());

        producer.close();
    }

}
