package org.scala.day1

/**
  * companion objects  伙伴对象
  *
  */
class CompanionObject(val name: String) {

  def talk(message: String) = println(name + "says" + message)

  def id(): String = name

}

/**
  * override 关键字是必需的. 这个关键字可以防止你因无意中拼写错误而引入新方法。
  */
class ExtendsClass(override val name: String, val number: Int) extends CompanionObject(name) {

  override def talk(message: String): Unit = {
    println(name + "with number" + number + "says" + message)
  }

  override def id(): String = number.toString

}

/**
  * companion objects
  */
object CompanionObject {

  def main(args: Array[String]): Unit = {
    val extendsClass = new ExtendsClass("yoda", 4)
    extendsClass.talk("Extend or extend not. There is no try.")
  }

}
