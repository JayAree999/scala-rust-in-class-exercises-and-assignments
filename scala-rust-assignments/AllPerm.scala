object AllPerm extends App {
  def allPerm(n: Int): List[List[Int]] = n match {
    case 0 => List(List(1))
    case 1 => List(List(1))
    case _ => prev(n, allPerm(n-1)) // 2 , List(List(1))
  }

  def prev(n: Int,xs: List[List[Int]]): List[List[Int]] = xs match{
    case Nil => Nil
    case _ => swap(n, xs.head,0,List[List[Int]]()) ::: prev(n, xs.tail) // swap(2, List(1) add to prev(n, List()) next loop /// List[List[Int]]
  }

  def swap(n: Int, xs: List[Int], idx: Int, ret : List[List[Int]]): List[List[Int]] = xs match {
    case Nil => Nil
    case _ =>
      if (idx>xs.length) then return ret
      val (left, right) = xs.splitAt(idx)
      swap(n, xs, idx+1,ret :+ (left ::: n :: right))
  }
  println(allPerm(3))

}