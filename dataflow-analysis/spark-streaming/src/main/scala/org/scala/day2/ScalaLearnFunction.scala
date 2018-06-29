package org.scala.day2

class ScalaLearnFunction {
  /*
  * 定义一个函数 def func(函数名)(参数:参数类型...):String(返回类型(Unit是无返回类型))={函数体}
  * 没有return 最后一句是返回的数据
  * */
  def func (x: Int, y: String): String = {
    //    定义变量 无需指定类型
    val name = "Tom"
    val age = 20
    val word = y + x
    word + name + age
  }

}

object Main {
  def main (args: Array[String]): Unit = {
    val scalaLearnFunction = new ScalaLearnFunction
    val word = scalaLearnFunction.func(6, "abc")
    print(word)
  }
}