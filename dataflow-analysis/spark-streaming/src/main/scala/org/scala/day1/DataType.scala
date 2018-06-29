package org.scala.day1

/**
  * Scala 类型
  *
  * Scala 一起都是对象, 避免类似java的int（原生类型）和Integer(对象)的转换
  *
  * 1. Scala 是强类型,使用类型推断，通过语法线索推断出变量的类型.
  * 2. 实际上Scala是先对代码进行编译，然后在一行一行的执行代码的.
  *
  */
object DataType {

  def main(args: Array[String]): Unit = {

    println("hello surreal world")

    println(1 + 1)

    println((1).+(2))

    println(5 + 4 * 3)

    println(5.+(4.*(3)))

    println((5).+((4).*(3)))

    println("abc".size)

    println("abc" + 4)

    val a:Int = 4

    println(a + new String("abc"))

    println(4 + "1.0")


  }

}
