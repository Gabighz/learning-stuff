package exercises

/**
 * Done as part of the Scala and Functional Programming for Beginners course on Udemy by
 * Daniel Ciocarlanu.
 */

object Basic extends App {

  class Writer(firstName: String, surname: String, val yearOfBirth: Int) {
    def fullName() = s"$firstName $surname"
  }

  class Novel(name: String, yearOfRelease: Int, author: Writer) {
    def authorAge() = yearOfRelease - author.yearOfBirth
    def isWrittenBy(author: Writer) = this.author == author
    def copy(newYearOfRelease: Int) = new Novel(name, newYearOfRelease, author)
  }

  class Counter(n: Int = 0) {
    def currentCount() = n

    def inc =
      println("incrementing")
      new Counter(n + 1)

    def dec =
      println("decrementing")
      new Counter(n - 1)

    def increment(amount: Int): Counter =
      if (amount <= 0) this
      else inc.increment(amount-1)

    def decrement(amount: Int): Counter =
      if (amount <= 0) this
      else dec.decrement(amount-1)

    def print = println(n)
  }

  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  println(novel.authorAge())
  println(novel.isWrittenBy(author))
  println(novel.isWrittenBy(imposter))

  val counter = new Counter
  counter.inc.print
  counter.increment(10).print
}
