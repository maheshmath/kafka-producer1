package kafka.consumer;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;



public class KafkaConsumerDemo {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.7:9092"); // Kafka broker(s)
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create the consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        List<String> topics = new ArrayList<>();
        topics.add("sample-topic");

        consumer.subscribe(topics);

        try{
            while(true){
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(record->{
                    System.out.printf("Consumed message: key = %s, value = %s, partition = %d, offset = %d%n",
                    record.key(), record.value(), record.partition(), record.offset());
                });
            }
        }finally {
            consumer.close();
        }
    }

}
