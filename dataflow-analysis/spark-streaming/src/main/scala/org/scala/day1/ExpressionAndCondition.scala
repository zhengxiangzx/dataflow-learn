package org.scala.day1

/**
  * 表达式与条件
  *
  * 1. 注意这里没有指定变量的类型,Scala会推断变量类型
  * 2. val用来声明不变量，Nil是一个空列表
  *
  *
  */
object ExpressionAndCondition {

  def main(args: Array[String]): Unit = {

    println(5 < 6)

    println(5 <= 6)

    println(5 >= 2)

    println(5 != 2)

    val a = 1
    val b = 2

    if (b > a) {
      println("true")
    } else {
      println("false")
    }

    println(Nil)

    // 无法对0或Nil进行条件测试，这种行为与Scala的强类型和静态类型语言设计哲学相吻合。
    /**
      * 静态类型: 编译时知道变量类型
      * 动态类型: 运行时才知道一个变量类型
      *
      * 强类型: 不允许隐式转换的是强类型
      * 弱类型: 允许隐式转换的是弱类型
      *
      *
      */
//    if (0) {
//      println("true")
//    }
//    if (Nil) {
//      println("true")
//    }
  }

}
