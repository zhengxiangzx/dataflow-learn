package org.sparkstudy.day2

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 示例: reduceByKeyAndWindow(func, windowLength, slideInterval, [numTasks])
  * 在一个 (K, V) pairs 的 DStream 上调用时, 返回一个新的 (K, V) pairs 的 Stream,
  * 其中的每个 key 的 values 是在滑动窗口上的 batch 使用给定的函数 func 来聚合产生的.
  * Note（注意）: 默认情况下, 该操作使用 Spark 的默认并行任务数量（local model 是 2,
  * 在 cluster mode 中的数量通过 spark.default.parallelism 来确定）来做 grouping.
  * 您可以通过一个可选的 numTasks 参数来设置一个不同的 tasks（任务）数量.
  *
  */

object ReduceByKeyAndWindow {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ReduceByKeyAndWindow").setMaster("local[2]")

    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.checkpoint("D:/spark_out")

    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))

    pairs.print()

    val windowedWordCounts = pairs.reduceByKeyAndWindow((a: Int, b: Int) => (a + b), Seconds(30), Seconds(10), 2)

    windowedWordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
