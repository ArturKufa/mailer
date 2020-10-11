package org.arturkufa.importer;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.arturkufa.importer.model.Message;
import org.arturkufa.importer.model.User;
import org.arturkufa.importer.serdes.MessageSerializer;
import org.arturkufa.importer.serdes.UserSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Properties;

@Component
public class FlinkTableImporter {
    //todo zrób inner join message - user, co pozwoli ci na poranie tylko tych message
    // ktore maja juz zaczytanych userow, i tylko tych wrzucaj do bassy

    private StreamTableEnvironment flinkTableEnv;
    private Properties kafkaProperties;


    @PostConstruct
    private void setupFlinkEnvrioment() {
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        flinkTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
        kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", "localhost:9093");
        kafkaProperties.setProperty("group.id", "test_2");
        flinkTableEnv.getConfig().getConfiguration().set(
                ExecutionCheckpointingOptions.CHECKPOINTING_MODE, CheckpointingMode.EXACTLY_ONCE);
        flinkTableEnv.getConfig().getConfiguration().set(
                ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL, Duration.ofSeconds(10));
    }

    public void getMailDataSet() {
//todo opisac to w prezentacji:
        /*
        todo
         For streaming job, TableResult.collect() method or TableResult.print method guarantee
         end-to-end exactly-once record delivery. This requires the checkpointing mechanism to be enabled.
         By default, checkpointing is disabled. To enable checkpointing, we can set checkpointing properties
         (see the checkpointing config for details) through TableConfig.
         So a result record can be accessed by client only after its corresponding checkpoint completes.
         */
        flinkTableEnv.executeSql(
                "CREATE TABLE messageTable (" +
                        " messageId BIGINT," +
                        " senderId BIGINT," +
                        " receiverId BIGINT," +
                        " messageText STRING " +
                        ") WITH (" +
                        " 'connector' = 'kafka'," +
                        " 'topic' = 'mail'," +
                        " 'properties.bootstrap.servers' = 'localhost:9093'," +
                        " 'properties.group.id' = 'tableGroup'," +
                        " 'format' = 'json'," +
                        " 'json.fail-on-missing-field' = 'false', " +
                        " 'json.ignore-parse-errors' = 'true', " +
                        " 'scan.startup.mode' = 'earliest-offset'" +
                        ")");

       // flinkTableEnv.executeSql("SELECT messageId FROM messageTable").print();
        flinkTableEnv.sqlQuery("SELECT * FROM messageTable").execute().print();


        try {
            flinkTableEnv.execute("table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
