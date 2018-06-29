package org.scala.day2

class ImplicitLearn {
  def add (x: Int, y: Int): Int = {
    val sum = x + y
    sum
  }
  def printName(x:String):String={
    val name=x
    name
  }
}

object ImplicitLearn {
  def main (args: Array[String]): Unit = {
    import org.scala.day2.ImplicitMore._
    val result = new ImplicitLearn
    val sum = result.add(3.56, 4.5)
    println(sum)
    val name=result.printName(55653)
    println(name)
  }
}