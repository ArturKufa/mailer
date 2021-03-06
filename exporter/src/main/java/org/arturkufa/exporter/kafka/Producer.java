package org.arturkufa.exporter.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.arturkufa.exporter.generator.model.Message;
import org.arturkufa.exporter.generator.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vavr.API.unchecked;


/**
 * Runs in a dedicated thread. Contains core logic for producing event.
 */
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC_NAME = "mail";
    private KafkaProducer<String, String> kafkaProducer = null;
    private final String KAFKA_CLUSTER_ENV_VAR_NAME = "KAFKA_CLUSTER";
    ObjectMapper mapper = new ObjectMapper();

    public Producer() {
        LOGGER.info( "Kafka Producer running in thread {0}", Thread.currentThread().getName());
        Properties kafkaProps = new Properties();

        String defaultClusterValue = "localhost:9093";
        String kafkaCluster = System.getProperty(KAFKA_CLUSTER_ENV_VAR_NAME, defaultClusterValue);
        LOGGER.info( "Kafka cluster {0}", kafkaCluster);

        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.ACKS_CONFIG, "0");

        this.kafkaProducer = new KafkaProducer<>(kafkaProps);
    }

    public void produceMessage(List<Message> messageList) {
        messageList.stream()
                    .map(this::delay)
                    .map(m -> unchecked(() -> mapper.writeValueAsString(m)))
                    .map(json -> new ProducerRecord<>("mail", "key", json.apply()))
                    .forEach(this::sendRecord);
    }

    public void produceUser(List<User> userList) {
        userList.stream()
                .map(this::delay)
                .map(m -> unchecked(() -> mapper.writeValueAsString(m)))
                .map(json -> new ProducerRecord<>("user", "key", json.apply()))
                .forEach(this::sendRecord);
    }

    private Message delay(Message msg) {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private User delay(User user) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void sendRecord(ProducerRecord<String, String> record) {
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata rm, Exception excptn) {
                if (excptn != null) {
                    LOGGER.warn("Error sending message with key, {}",excptn.getMessage());
                } else {
                    LOGGER.info("Record sent: {}", record.value());
                }

            }
        });
    }
    private void produce() throws Exception {
        ProducerRecord<String, String> record = null;

        try {
            Random rnd = new Random();
            while (true) {

                for (int i = 1; i <= 10; i++) {
                    String key = "machine-" + i;
                    String value = String.valueOf(rnd.nextInt(20));
                    record = new ProducerRecord<>(TOPIC_NAME, key, value);

                      kafkaProducer.send(record, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata rm, Exception excptn) {
                            if (excptn != null) {
                                LOGGER.warn("Error sending message with key {}\n{}", key, excptn.getMessage());
                            } else {
                                LOGGER.info("Partition for key-value {}::{} is {}", key, value, rm.partition());
                            }

                        }
                    });
                    /*
                     * wait before sending next message. this has been done on
                     * purpose
                     */
                    Thread.sleep(1000);
                }

            }
        } catch (Exception e) {
            LOGGER.error("Producer thread was interrupted");
        } finally {
            kafkaProducer.close();

            LOGGER.info( "Producer closed");
        }

    }

}