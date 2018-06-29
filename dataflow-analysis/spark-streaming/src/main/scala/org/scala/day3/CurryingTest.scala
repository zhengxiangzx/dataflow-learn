package org.scala.day3

object CurryingTest {
  def main (args: Array[String]): Unit = {
    val sum = plainOldSum(1, 2)
    println("sum=" + sum)
    val first = plainSum(1)_
//    val second = first
    println(first)
  }

  def plainOldSum (x: Int, y: Int) = x + y

  def plainSum (x: Int)(y: Int)(z: Int) = x + y + z
//  def first(x:Int) = (y:Int)=> x+y
  //  val second=first(2) //柯里化函数调用过程
  //  val res=second(8)
  //  println(res)
}
