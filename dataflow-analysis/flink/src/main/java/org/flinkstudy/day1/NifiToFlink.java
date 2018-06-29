package org.flinkstudy.day1;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.nifi.NiFiDataPacket;
import org.apache.flink.streaming.connectors.nifi.NiFiSource;
import org.apache.flink.util.Collector;
import org.apache.nifi.remote.client.SiteToSiteClient;
import org.apache.nifi.remote.client.SiteToSiteClientConfig;

import java.nio.charset.StandardCharsets;

/**
 * Site to Site
 *
 *
 *
 */
public class NifiToFlink {
    public static void main(String[] args)throws Exception  {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SiteToSiteClientConfig clientConfig = new SiteToSiteClient.Builder().url("http://localhost:8080/nifi")
                .portName("Data For Flink").requestBatchCount(20000).buildConfig();

        SourceFunction<NiFiDataPacket> nifiSource = new NiFiSource(clientConfig);

        DataStream<NiFiDataPacket> streamSource = env.addSource(nifiSource);

        DataStream<Tuple2<String, Integer>> counts = streamSource.flatMap(new Tokenizer()).keyBy(0).sum(1);
        counts.print();

        env.execute();
    }
    // USER  FUNCTIONS
    public static final class Tokenizer implements
            FlatMapFunction<NiFiDataPacket, Tuple2<String, Integer>> {

        @Override
        public void flatMap(NiFiDataPacket dataPacket, Collector<Tuple2<String, Integer>> out) throws Exception {

            String[] tokens = new String(dataPacket.getContent(), StandardCharsets.UTF_8).split(" ");

            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }

            }

        }
    }
}
