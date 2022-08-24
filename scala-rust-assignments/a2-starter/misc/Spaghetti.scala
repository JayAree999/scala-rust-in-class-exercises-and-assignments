import scala.annotation.tailrec

object Spaghetti extends App {

  def lookAndSay(previous: String): LazyList[String] = {
    def next(num: String): String =
      if num.isEmpty then ""
      else {
        if num.tail.isEmpty then return "1" + num.head + ""
        else {
          val size = (num takeWhile (_ == num.head)).length

          size.toString + num.head  + next(num.drop(size))
        }
      }

    val x = next(previous)
    x #:: lookAndSay(x)
  }
    def spaghetti: LazyList[String] = lookAndSay("1")


def ham : LazyList[String] = loop(1).flatMap(gray)
  def gray(n: Int): List[String] =
    if (n == 0) List("")
    else {
      val lower = gray(n - 1)
      (lower map { "0" + _ }) ::: (lower.reverse map { "1" + _ })
    }
   val strings = Map(0 -> List(""))
  def grayMemoized(n: Int): List[String] = {
    if (!strings.contains(n)) {
      strings + (n -> ((grayMemoized(n - 1) map { "0" + _ }) :::
        (grayMemoized(n - 1).reverse map { "1" + _ })))
    }
    strings(n)
  }
  def loop(i: Int): LazyList[Int] = {
    i #:: loop(i+1)
  }
  println(spaghetti.take(5).toList)

  //That is, the ham stream starting with "0", "1", "00", "01", "11", "10", "000", "001", "011", "010",
  //"110","111", "101", "100", ...
  println(ham.take(5).toList)
}

