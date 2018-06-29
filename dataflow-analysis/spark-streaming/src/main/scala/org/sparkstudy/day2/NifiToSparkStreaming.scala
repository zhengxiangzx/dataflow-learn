package org.sparkstudy.day2

import java.nio.charset.StandardCharsets

import org.apache.nifi.remote.client.SiteToSiteClient
import org.apache.nifi.spark.NiFiReceiver
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Nifi + SparkStreaming
  * http://localhost:8080/nifi/
  *
  *
  *
  *
  */
object NifiToSparkStreaming {
  def main (args: Array[String]): Unit = {

    val conf = new SiteToSiteClient.Builder().url("http://localhost:8080/nifi").portName("sparkstreaming").requestBatchCount(1000).buildConfig()
    val config = new SparkConf().setAppName("Nifi_Spark_Phoenix").setMaster("local[4]")
    val sc = new SparkContext(config)
    implicit val ssc = new StreamingContext(sc, Seconds(3))
    val numStreams = 1
    val nifiStreams = (1 to numStreams).map(i => ssc.receiverStream(new NiFiReceiver(conf, StorageLevel.MEMORY_ONLY)))
    val lines = ssc.union(nifiStreams)
    //    lines.count().print()
    implicit val lineStrings = lines.map(dataPacket => new String(dataPacket.getContent, StandardCharsets.UTF_8))
    val words = lineStrings.flatMap(_.split(" ")).map(x => (x, 1))

    val wordCounts = words.reduceByKey(_ + _)

    wordCounts.print()
    updateStateByKey

    ssc.start()
    ssc.awaitTermination()
  }

  def updateStateByKey (implicit lineStrings: DStream[String], ssc: StreamingContext): Unit = {
    ssc.checkpoint("D:/spark_out1")
    val words = lineStrings.flatMap(_.split(" ")).map(x => (x, 1))

    val addFunc = (currValues: Seq[Int], prevValueState: Option[Int]) => {

      val currentCount = currValues.sum

      val previousCount = prevValueState.getOrElse(0)

      Some(currentCount + previousCount)
    }
    val wordCounts = words.updateStateByKey(addFunc)

    wordCounts.print()

  }

}
