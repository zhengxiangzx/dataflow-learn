package org.scala.day1

/**
  * 在scala中，public是默认的可见级别，即对这个函数将对所有调用者可见
  *
  * 元组是一个固定长度的对象集合，在许多其他函数式编程语言中也会发现这个模式
  * 元组中的对象可以具有不同类型
  *
  */

object WhileAndTuple {

  def whileLoop: Unit = {
    var i = 1
    while (i <= 3) {
      println(i)
      i += 1
    }

  }

  def forLoop: Unit = {
    println("for loop using Java-style iteration")
    for (i <- 0 until 3) {
      println(i)
    }

  }

  def rubyStyleForLoop: Unit = {
    println("for Loop using Ruby-Style iteration")
    val args = List("sohu", "cyou", "bi")
    args.foreach { arg => println(arg) }
  }

  def rangeStudy: Unit = {

    val range = 0 until 10
    println(range.start)
    println(range.end)

    println(range.step)
    println((0 to 10) by 5)
    println((0 until 10) by 6)
    println((10 until 0) by -1)

    println('a' to 'e')

  }

  def tupleStudy: Unit = {
    val person = ("sohu", "cyou")
    println(person._1)
    println(person._2)
    //Scala使用元组而不是列表进行多赋值
    val (x, y) = (1, 2)
    // 由于元组具有固定长度，Scala可以基于每个元组元素的值对其进行[静态类型检查]
    //    val (a, b) = (1, 2, 3)
    println((x, y))

  }

  def main(args: Array[String]): Unit = {
    whileLoop
    forLoop
    rubyStyleForLoop
    rangeStudy
    tupleStudy
  }

}
