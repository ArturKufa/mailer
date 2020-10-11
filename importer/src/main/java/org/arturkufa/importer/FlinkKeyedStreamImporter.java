//package org.arturkufa.importer;
//
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.KeyedStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
//import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
//import org.apache.flink.streaming.api.windowing.time.Time;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
//import org.arturkufa.importer.model.Message;
//import org.arturkufa.importer.model.User;
//import org.arturkufa.importer.serdes.MessageSerializer;
//import org.arturkufa.importer.serdes.UserSerializer;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Properties;
//
//@Component
//public class FlinkKeyedStreamImporter {
//    private StreamExecutionEnvironment flinkEnv;
//    private Properties kafkaProperties;
//
//
//    @PostConstruct
//    private void setupFlinkEnvrioment() {
//        flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//        flinkEnv.enableCheckpointing(10_000);
//        kafkaProperties = new Properties();
//        kafkaProperties.setProperty("bootstrap.servers", "localhost:9093");
//        kafkaProperties.setProperty("group.id", "test_2");
//    }
//
//    public void getMailDataSet() {
//
//        //todo ogarnac po co to mi jest
//        KeyedStream<Message, Integer> mailStream = flinkEnv.addSource(new FlinkKafkaConsumer<>(
//                "mail", new SimpleStringSchema(), kafkaProperties))
//                .map(new MessageSerializer())
//                .keyBy(Message::getSenderId);
//        KeyedStream<User, Integer> userStream = flinkEnv.addSource(new FlinkKafkaConsumer<>(
//                "user", new SimpleStringSchema(), kafkaProperties))
//                .map(new UserSerializer())
//                .keyBy(User::getId);
//
//        userStream.print();
//
//
//        mailStream.join(userStream)
//                .where(Message::getSenderId)
//                .equalTo(User::getId)
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
//                //logika wykona sie PO zakonczeniu okna, czyli po 10 sekundach spokoju
//                .apply((m, u) -> m.toString() + u.toString())
//                .print();
//
//        try {
//            flinkEnv.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
