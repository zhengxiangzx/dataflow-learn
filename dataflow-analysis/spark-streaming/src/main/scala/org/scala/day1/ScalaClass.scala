package org.scala.day1

/**
  * 每个类都有一个主构造器，这个构造器和类的定义“交织”在一起,主构造器执行类体中的所有语句.
  * 类中的字段自动带getter和setter方法.
  * 如果字段是val, 则只生成getter方法.
  * 如果想要主构造器成为私有的,可以在参数列表前放置private关键字.
  *
  * 辅助构造器是可选的，它们都叫做this.
  *
  *
  */
class Person(var first_name: String) {
  println("Outer constructor")

  // 注意这个this方法，它是这个类的第二个构造器。一开始,这个方法通过this调用只有一个first_name参数的主构造器
  def this(first_name: String, last_name: String) {
    this(first_name)
    println("Inner constructor")
  }

  def talk() = println("Hi")

}

object ScalaClass {
  def main(args: Array[String]): Unit = {

    val gump = new Person("Forrest", "Gump")
    gump.first_name = ""
    println(gump.first_name)

  }
}

/**
  * 在Scala中，可以只用一行代码定义那些只有属性而没有方法或构造器的简单类
  *
  */
//class Person(val firstName: String, val lastName: String)
