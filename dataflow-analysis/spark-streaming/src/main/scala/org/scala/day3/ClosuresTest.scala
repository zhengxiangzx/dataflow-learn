package org.scala.day3

//object ClosuresTest {
//  def main (args: Array[String]): Unit = {
//    println("muliplier(1) value = " + multiplier(1))
//    println("muliplier(2) value = " + multiplier(2))
//  }
//
//  var factor = 3
//  val multiplier = (i: Int) => i * factor
//}

package otherscope {

  class Foo {
    // a method that takes a function and a string, and passes the string into
    // the function, and then executes the function
    def exec (f: (String) => Unit, name: String) {
      f(name)
    }
  }

}

object ClosureExample extends App {
  var hello = "Hello"

  def sayHello (name: String) {
    println(s"$hello, $name")
  }

  // execute sayHello from the exec method foo
  hello="ssssssss"
  val foo = new otherscope.Foo
  foo.exec(sayHello, "Al")

  // change the local variable 'hello', then execute sayHello from
  // the exec method of foo, and see what happens
  hello = "Hola"
  foo.exec(sayHello, "Lorenzo")
}
