package org.scala.day1

/**
  * trait 特质
  * 可以将Scala的trait看成是Java的接口外加一个接口的实现。
  * 我们将trait看成是一部分类的实现的.
  *
  */
class ManClass(val name: String)

trait Nice {
  def greet() = println("Howdily doodily")
}

class SonMan(override val name: String) extends ManClass(name) with Nice {

}

object TraitClass extends App {

  val ted = new SonMan("ted")
  ted.greet()

}
