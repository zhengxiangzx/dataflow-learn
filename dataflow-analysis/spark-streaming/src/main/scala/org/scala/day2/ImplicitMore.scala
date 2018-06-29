package org.scala.day2

/*
* 隐式转换
* */
object ImplicitMore {
  implicit def doubleToInt (x: Double) = x.toInt

  implicit def intToString (x: Int) = x.toString
}
