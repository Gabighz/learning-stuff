package exercises

import scala.language.postfixOps

/**
 * Done as part of the Scala and Functional Programming for Beginners course on Udemy by
 * Daniel Ciocarlanu.
 */

object MethodNotations extends App {
  class Person(name: String, favMovie: String, val age: Int = 0) {
    def +(nickname: String) = new Person(s"$name ($nickname)", favMovie, age)
    def unary_+ : Person = new Person(name, favMovie, age + 1)

    def learns(thing: String) = s"$name learns $thing"
    def learnsScala = this learns "Scala"

    def apply() = s"Hi, my name is $name and I like $favMovie"
    def apply(n: Int) = s"$name watched $favMovie $n times"
  }

  val mary = new Person("Mary", "Inception")

  println((mary + "the rockstar")())
  println((+mary).age)
  println(mary learnsScala)
  println(mary(10))
}
