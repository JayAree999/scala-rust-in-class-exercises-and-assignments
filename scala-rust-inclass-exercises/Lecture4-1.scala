import scala.None
import scala.annotation.tailrec

object Lecture4 extends App{

  def find(xs: List[(Int, String)], key: Int): Option[String] = {
    if (xs.isEmpty) None
    else {
      if xs.head._1 == key then
        Some(xs.head._2)
      else
        find(xs.tail, key)
    }
  }



  def rev(xs: List[Int]): List[Int] = {
    @tailrec
    def tailRev(xs: List[Int], acc : List[Int]) : List[Int] = {
      if xs.isEmpty then acc
      else

      tailRev(xs.tail, xs.head :: acc) // List(1,2) , List(3,4)
    }
//    if (xs.isEmpty) then return List()
//    xs.last :: rev(xs.take(xs.length-1))

    tailRev(xs,List[Int]())
  }

  def fib(n: Int): Long = {

    def tailfib(n: Int, i: Long, j: Long): Long =

    {
      if (n == 0) return j
      if(n == 1) return i
      else {
        tailfib(n-1, i+j, i )
      }

    }
    //n,  acc of n-1, acc of n-2
    tailfib(n, 1, 0)
  }
  println(fib(4)) //2+3
  println(rev(List(1,2,3,4)))

}
