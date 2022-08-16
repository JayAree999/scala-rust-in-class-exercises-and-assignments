object Lecture3 extends App {
  def sumPairList(xs: List[(Int, Int)]): Int = xs match{
    case Nil => 0
    case _ =>
      xs.head._1 + xs.head._2 + sumPairList(xs.tail) // Int,/Int

  }

//  adds up all the numbers (both in the first and second
//    coordinates). For example,
//  sumPairList(List((5,2), (1,4), (2, 7))) ==> 21 // 5+2+1+4+2+7
  def firsts(xs: List[(Int, Int)]): List[Int] = xs match {
  case Nil => List()
  case _ => xs.head._1 :: firsts(xs.tail)
}
//  returns a list that extracts the first coordinate.
//  firsts(List((5,2), (1,4), (2, 7))) ==> List(5, 1, 2)
  def seconds(xs: List[(Int, Int)]): List[Int] = xs match {
  case Nil => List()
  case _ => xs.head._2 :: seconds(xs.tail)
}
//  returns a list that extracts the second coordinate.
//  seconds(List((5,2), (1,4), (2, 7))) ==> List(2, 4, 7)
  def pairSumList(xs: List[(Int, Int)]): (Int, Int) = {
    def sum(xs : List[Int]) : Int = xs match {
      case Nil => 0
      case _ => xs.head+sum(xs.tail)
    }
  (sum(firsts(xs)), sum(seconds(xs))) //
}
//  returns a pair where the first number is the sum
//  of the first coordiates, and the second number is the sum of the second coordiates.
//    pairSumList(List((5,2), (1,4), (2, 7))) ==> (8, 13) // 5+1+2 and 2+4+7
println(pairSumList(List((5,2), (1,4), (2, 7))))// 5+1+2 and 2+4+7)
}
