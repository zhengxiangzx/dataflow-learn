import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time


object WindowWordCount {
  def main (args: Array[String]): Unit = {
    val env=StreamExecutionEnvironment.getExecutionEnvironment
    val text=env.socketTextStream("localhost",9999)
//    val counts=text.flatMap{_.toLowerCase.split(" ") filter{_.nonEmpty}}
//      .map{(_,1)}.keyBy(0).timeWindow(Time.seconds(5)).sum(1)
    //val counts=text.flatMap(_.split(" ")).map(x=>(x,1)).keyBy(0).sum(1)
  //  val counts=text.flatMap{_.split(" ")}.filter{_ !="zheng"}.map{x=>(x,1)}.keyBy(0).sum(1)
    val counts=text.flatMap{_.split(" ")}.map{x=>(x,1)}.keyBy(0).window(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(5))).sum(1)
    //val conts=text.flatMap{_.split(" ")}.filter{_ !="zheng"}.map{x=>(x,1)}.keyBy(1).sum(2)
    counts.print()
    env.execute("Window WordCount2")
  }
}
