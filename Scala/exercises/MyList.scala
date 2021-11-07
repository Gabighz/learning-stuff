package exercises

/**
 * Done as part of the Scala and Functional Programming for Beginners course on Udemy by
 * Daniel Ciocarlanu.
 *
 * This is a generic singly linked list with some utility functions (map, filter, flatMap)
 */

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](value: B): MyList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = s"[${printElements}]"

  // higher-order functions (take funcs as input or return funcs)
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]
}

object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](value: B): MyList[B] = new Cons(value, Empty)
  def printElements = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]) : MyList[B] = list
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](value: B): MyList[B] = new Cons(value, this)
  def printElements: String =
    if (t.isEmpty) s"${h}"
    else s"$h ${t.printElements}"

  def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer(h), t.map(transformer))

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)
}

object ListTest extends App {
  val listOfInts = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherListOfInts = new Cons(4, new Cons(5, Empty))
  println(listOfInts.head)
  println(listOfInts.add(4).head)
  print(listOfInts.toString)

  val listOfString = new Cons("Hello", new Cons("Scala", Empty))
  println(listOfString.toString)

  println(listOfInts.map(_ * 2).toString)

  println(listOfInts.filter(_ % 2 == 0).toString)

  println((listOfInts ++ anotherListOfInts).toString)

  println(listOfInts.flatMap(x => new Cons(x, new Cons(x + 1, Empty))))

  /*
    The above is syntactic sugar for something such as the following:
    This creates an object that has an anonymous class and behaves like a function
    println(listOfInts.map(new Function1[Int, Int] {
      override def apply(elem: Int): Int = elem * 2
    }).toString)
 */
}