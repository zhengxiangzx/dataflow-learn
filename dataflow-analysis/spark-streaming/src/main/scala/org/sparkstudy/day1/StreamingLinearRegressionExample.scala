package org.sparkstudy.day1

import org.apache.spark.SparkConf
// $example on$
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD
// $example off$
import org.apache.spark.streaming._

/**
  * 样例: 流式线性回归 - Streaming linear regression
  *
  * 注:textFileStream 在windows下不能监控文件创建、移动等操作
  *    通过py_tools子模块的iris_newdata来自动生成 训练和测试 的数据集。
  *
  */
object StreamingLinearRegressionExample {

  /**
    * 在一个数据流上训练回归模型，然后在另一个数据流上进行预测，这两个数据流中，数据都以文本的形式存入两个不同的目录。
    * 文本文件的行必须用如下格式标记根据点：'(y,[x1,x2,x3,...,xn])'，y是标签,n 表示特征的数量。
    * 用于训练和测试的n必须是一致的（拥有相同维度的特征）。 
    * 使用方法:StreamingLinearRegressionExample <训练目录> <测试目录>
    *  在本机运行时，使用两个目录：训练目录和测试目录，每五分钟更新一次，每个数据点两个特征，调用:
    *  $ bin/run-example mllib.StreamingLinearRegressionExample trainingDir testDir
    *  随着不断的向trainingDir添加文本文件，模式会持续更新。
    *  每次向testDir添加文本文件时，都能看到根据当前模式预测出的结果。
    *
    */
  def main(args: Array[String]): Unit = {

    var trainingDir = "/mllib/training"
    var testDir = "/mllib/test"

    if (args.length != 2) {
      System.err.println("Usage: StreamingLinearRegressionExample <trainingDir> <testDir>")
      //      System.exit(1)
      println("Read Local Parameter!")
      trainingDir = getClass().getResource(trainingDir).getPath
      testDir = getClass().getResource(testDir).getPath
    } else {
      trainingDir = args(0)
      testDir = args(1)
    }

    val conf = new SparkConf().setAppName("StreamingLinearRegressionExample")
    conf.setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    // $example on$
    val trainingLines = ssc.textFileStream(trainingDir)
    trainingLines.print()
    val trainingData = trainingLines.map(LabeledPoint.parse)

    val testData = ssc.textFileStream(testDir).map(LabeledPoint.parse)

    val numFeatures = 4
    val model = new StreamingLinearRegressionWithSGD()
      .setInitialWeights(Vectors.zeros(numFeatures))

    model.trainOn(trainingData)

    // (lp.label, lp.features)   参考值, 特征集合
    // -------------------------------------------
    // Time: 1527588155000 ms
    // -------------------------------------------
    // (0.0,-0.01386366965641772)
    // (1.0,1.3260283849526537)
    // (2.0,1.9560477835247314)
    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()

    ssc.start()
    ssc.awaitTermination()
    // $example off$

    ssc.stop()

  }

}
