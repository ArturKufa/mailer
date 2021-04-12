package org.arturkufa.importer;

import io.vavr.Tuple2;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.arturkufa.importer.mappers.MailReceiverMap;
import org.arturkufa.importer.mappers.MailSenderMap;
import org.arturkufa.importer.model.Mail;
import org.arturkufa.importer.model.User;
import org.arturkufa.importer.serdes.MessageSerializer;
import org.arturkufa.importer.serdes.UserSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class FlinkMailByUserPrinter {
    private StreamExecutionEnvironment flinkEnv;
    private Properties kafkaProperties;


    @PostConstruct
    private void setupFlinkEnvrioment() {
        flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        flinkEnv.enableCheckpointing(100_000);
        flinkEnv.setStateBackend(new MemoryStateBackend(5 * 1024 * 1024, true));
        kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", "localhost:9093");
        kafkaProperties.setProperty("group.id", "test_2");
    }

    public void getMailDataSet() {

        KeyedStream<Mail, Integer> mailByReceiver = flinkEnv.addSource(new FlinkKafkaConsumer<>(
                "mail", new SimpleStringSchema(), kafkaProperties))
                .map(new MessageSerializer())
                .keyBy(Mail::getReceiverId);
        KeyedStream<Mail, Integer> mailBySender = flinkEnv.addSource(new FlinkKafkaConsumer<>(
                "mail", new SimpleStringSchema(), kafkaProperties))
                .map(new MessageSerializer())
                .keyBy(Mail::getSenderId);
        KeyedStream<User, Integer> userStream = flinkEnv.addSource(new FlinkKafkaConsumer<>(
                "user", new SimpleStringSchema(), kafkaProperties))
                .map(new UserSerializer())
                .keyBy(User::getId);

        mailBySender.print();
        userStream.print();

        KeyedStream<Tuple2<User, Mail>, Integer> mailWithReceiver = userStream.connect(mailByReceiver)
                .flatMap(new MailReceiverMap())
                .keyBy(t -> t._2().getMessageId());

        KeyedStream<Tuple2<User, Mail>, Integer> mailWithSender = userStream.connect(mailBySender)
                .flatMap(new MailSenderMap())
                .keyBy(t -> t._2().getMessageId());

        try {
            flinkEnv.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
