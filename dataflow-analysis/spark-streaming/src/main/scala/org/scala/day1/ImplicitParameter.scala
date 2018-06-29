package org.scala.day1

class Student(var name: String) {

  def formatStudent(implicit outputFormat: OutputFormat) = {
    outputFormat.first + " " + this.name + " " + outputFormat.second
  }

}

class OutputFormat(var first: String, var second: String)

object ImplicitParameter {

  def main(args: Array[String]): Unit = {
    //编译器会在方法省略隐式参数的情况下去搜索作用域内的隐式值作为缺少参数
    implicit val outputFormat = new OutputFormat("<<", ">>")
    println(new Student("tianhonghan").formatStudent)
  }

}

object Stringutils {

  implicit class StringImprovement(val s: String) { //隐式类
    def increment = s.map(x => (x + 1).toChar)
  }

}

object ImplicitMain {
  def main(args: Array[String]): Unit = {
    //编译器在mobin对象调用increment时发现对象上并没有increment方法，
    //此时编译器就会在作用域范围内搜索隐式实体，
    //发现有符合的隐式类可以用来转换成带有increment方法的StringImprovement类，最终调用increment方法
    import Stringutils._
    println("mobin".increment)
  }

}
