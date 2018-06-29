package org.sparkstudy.day2

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 示例: updateStateByKey(func)
  * 返回一个新的 "状态" 的 DStream，其中每个 key 的状态通过在 key 的先前状态应用给定的函数和 key 的新 valyes 来更新.
  * 这可以用于维护每个 key 的任意状态数据.
  * 请注意, 使用 updateStateByKey 需要配置的 checkpoint （检查点）的目录
  *
  */
object UpdateStateByKey {
  def main(args: Array[String]): Unit = {
    val sc = new SparkConf().setAppName("updateStateByKeyExample").setMaster("local[2]")
    val ssc = new StreamingContext(sc, Seconds(5))

    ssc.checkpoint("D:/spark_out1")
    val lines = ssc.socketTextStream("localhost", 9999)
    lines.print()

    val words = lines.flatMap(_.split(" ")).map(x => (x, 1))

    val addFunc = (currValues: Seq[Int], prevValueState: Option[Int]) => {

      val currentCount = currValues.sum

      val previousCount = prevValueState.getOrElse(0)

      Some(currentCount + previousCount)
    }
    val wordCounts = words.updateStateByKey(addFunc)

    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()

  }

}
