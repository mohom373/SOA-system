package se.liu.ida.tdp024.account.logging;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaLogging {

    public void sendToKafka(String channel , String message) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        producer.initTransactions();
        try{
            System.out.println("try");
            producer.beginTransaction();
            producer.send(new ProducerRecord<>(channel, "Can't find", message));
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            ;
            System.out.println("Catch");
            producer.close();
        } catch (final KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            System.out.println("Catch2");
            producer.abortTransaction();
        }
        producer.close();
    }
}
