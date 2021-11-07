package lectures.basics

/**
  * Done as part of the Scala and Functional Programming for Beginners course on Udemy by
  * Daniel Ciocarlanu.
  */

object Recursion extends App {

  def concat(s: String, n: Int, accumulator: String = ""): String = {
      if (n <= 0) accumulator
      else concat(s, n-1, s + accumulator)
  }

  println(concat("h", 5))

  def isPrime(n: Int): Boolean = {
    def isPrimeHelper(t: Int, isStillPrime: Boolean = true) : Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeHelper(t - 1, n % t != 0 && isStillPrime)

    isPrimeHelper(n / 2)
  }

  println(isPrime(2003))

  def fibonacci(n: Int) : Int = {
    def fibHelper(i: Int, last: Int, nextToLast: Int): Int =
      if (i >= n) last
      else fibHelper(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fibHelper(2, 1, 1)
  }

  println(fibonacci(8))
}
