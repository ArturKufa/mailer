package org.arturkufa.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.arturkufa.importer.model.Mail;
import org.arturkufa.importer.model.User;
import org.arturkufa.importer.serdes.MessageSerializer;
import org.arturkufa.importer.serdes.UserSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

//@Component //todo uncomment if want to run it
public class FlinkStreamImporter {
    private StreamExecutionEnvironment flinkEnv;
    private Properties kafkaProperties;


    @PostConstruct
    private void setupFlinkEnvrioment() {
        flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", "localhost:9093");
        kafkaProperties.setProperty("group.id", "test_2");
    }

    public void getMailDataSet() {

        DataStream<Mail> mailStream = flinkEnv.addSource(new FlinkKafkaConsumer<>(
                "mail", new SimpleStringSchema(), kafkaProperties))
                .map(new MessageSerializer());
        DataStream<User> userStream = flinkEnv.addSource(new FlinkKafkaConsumer<>(
                "user", new SimpleStringSchema(), kafkaProperties))
                .map(new UserSerializer());


        mailStream.join(userStream)
                  .where(Mail::getSenderId)
                  .equalTo(User::getId)
                  .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                //logika wykona sie PO zakonczeniu okna, czyli po 10 sekundach spokoju
                .apply((m, u) -> m.toString() + u.toString())
                .print();

        try {
            flinkEnv.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
