package org.arturkufa.importer;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.CatalogDatabaseImpl;
import org.apache.flink.table.catalog.GenericInMemoryCatalog;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
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
                ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL, Duration.ofSeconds(2));

        //todo domyslnie tworzony jest inMemoryCatalog
//        GenericInMemoryCatalog inMemoryCatalog = new GenericInMemoryCatalog("inMemory");
//        configCatalog(inMemoryCatalog);
//        flinkTableEnv.registerCatalog("inMemory", inMemoryCatalog);
    }

    private void configCatalog(GenericInMemoryCatalog inMemoryCatalog) {
        try {
            inMemoryCatalog.createDatabase("db",
                    new CatalogDatabaseImpl(new HashMap<>(), null),
                    true);
        } catch (DatabaseAlreadyExistException e) {
            e.printStackTrace();
        }
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
                        " 'properties.group.id' = 'tableGroup_2'," +
                        " 'format' = 'json'," +
                        " 'json.fail-on-missing-field' = 'false', " +
                        " 'json.ignore-parse-errors' = 'true', " +
                        " 'scan.startup.mode' = 'earliest-offset'" +
                        ")");

       // flinkTableEnv.executeSql("SELECT messageId FROM messageTable").print();
        //todo to gdy chce miec jawnie podany katalog
       // flinkTableEnv.sqlQuery("SELECT * FROM inMemory.db.messageTable").execute().print();
       flinkTableEnv.sqlQuery("SELECT * FROM messageTable").execute().print();
        System.out.println("CATALOGS: " + flinkTableEnv.listCatalogs());


        try {
            flinkTableEnv.execute("table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
