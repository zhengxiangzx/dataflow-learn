import java.util.Properties

import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08

object KafkaConnectLearn {
  def main (args: Array[String]) {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.enableCheckpointing(1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "10.129.129.159:9092")
    properties.setProperty("zookeeper.connect","10.129.129.140:2181")
    properties.setProperty("group.id", "test00912")
//    val myConsumer = new FlinkKafkaConsumer08[String]("pc_reg", new SimpleStringSchema(), properties)
//    myConsumer.setStartFromEarliest()
//    myConsumer.setStartFromTimestamp(1000)
//    myConsumer.setStartFromGroupOffsets()
//    val stream=env.addSource(myConsumer)
    val stream = env.addSource(new FlinkKafkaConsumer08[String]("M1512611924144_heart", new SimpleStringSchema(), properties))
    val lines=stream.flatMap(_.split("\01"))
    val ss=lines.
    env.execute("kafka consumer")
  }
}
