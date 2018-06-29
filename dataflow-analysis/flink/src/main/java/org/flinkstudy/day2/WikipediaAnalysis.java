///**
// * Copyright (C), 2017-2018, 北京畅游天下网络技术有限公司
// *
// * @PROJECT_NAME: 魔盒系统
// * @FileName: WikipediaAnalysis
// * @Author: tianhonghan
// * @Date: 2018/5/2 16:48
// * @Description: 监控wiki编辑流
// * @History:
// * @ <author>          <time>          <version>          <desc>
// */
//package org.flinkstudy.day2;
//
//import org.apache.flink.api.common.functions.FoldFunction;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.api.java.functions.KeySelector;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.KeyedStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.windowing.time.Time;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
//import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
//import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;
//
///**
// * 〈功能简述〉<br> 〈监控wiki编辑流〉
// *
// * @Author tianhonghan
// * @Date 2018/5/2 16:48
// * @since 1.0.0
// */
//public class WikipediaAnalysis {
//
//    public static void main(String[] args) throws Exception {
//        //首先需要创建一个StreamExecutionEnvironment（如果你在编写是批处理程序，需要创建ExecutionEnvironment)
//        //它被用来设置运行参数。当从外部系统读取数据的时候，它也被用来创建源（sources）
//        final StreamExecutionEnvironment see = StreamExecutionEnvironment
//                .getExecutionEnvironment();
//        //添加读取 Wikipedia IRC 日志的源（sources）
//        DataStream<WikipediaEditEvent> edits = see.addSource(new WikipediaEditsSource());
//        //需要对时间窗口中每个唯一用户的编辑字节数求和。为了使数据流包含 key，我们需要提供一个KeySelector
//        KeyedStream<WikipediaEditEvent, String> keyedEdits = edits
//                .keyBy(new KeySelector<WikipediaEditEvent, String>() {
//                    @Override
//                    public String getKey(WikipediaEditEvent event) {
//                        return event.getUser();
//                    }
//                });
//        DataStream<Tuple2<String, Long>> result = keyedEdits
//                .timeWindow(Time.seconds(5))
//                .fold(new Tuple2<>("", 0L),
//                        new FoldFunction<WikipediaEditEvent, Tuple2<String, Long>>() {
//                            @Override
//                            public Tuple2<String, Long> fold(Tuple2<String, Long> acc,
//                                    WikipediaEditEvent event) {
//                                acc.f0 = event.getUser();
//                                acc.f1 += event.getByteDiff();
//                                return acc;
//                            }
//                        });
//
//        result
//                .map(new MapFunction<Tuple2<String, Long>, String>() {
//                    @Override
//                    public String map(Tuple2<String, Long> tuple) {
//                        return tuple.toString();
//                    }
//                })
//                .addSink(new FlinkKafkaProducer010<String>(
//                        "10.129.129.22:9092,10.129.129.163:9092,10.129.129.67:9092", "wiki-results",
//                        new SimpleStringSchema()));
//
//        see.execute();
//    }
//
//}