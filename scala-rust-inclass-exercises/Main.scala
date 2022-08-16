object l6 extends App{
  def unzip(xs: List[(Int,Int)]): (List[Int], List[Int]) = xs match {
    case Nil => (Nil,Nil)
    case (x,y)::t   => (x :: unzip(t)._1 , y::unzip(t)._2) // x is 1,  t is xs.tail which is next loop or tuple , so unzip(t)._1 is "1".
  }

  def countWhile[T](xs: List[T], key: T): Int = {
    xs.filter(_ == key).length
  }

  def topK(xs: List[Int], k: Int): List[Int] = {

    def helper(xs: List[Int], unique: List[Int]): List[(Int, Int)] = unique match { // xs.tail is next thing on list unique =>
      case Nil => Nil
      case _ => (unique.head, countWhile(xs, unique.head)) :: helper(xs, unique.tail)
    }

    val ans = helper(xs, xs.distinct).sortBy(_._2).reverse
    unzip(ans)._1.take(k)
  }

      println(unzip(List((1, 2), (1, 2))))
      println(countWhile(List(1, 1, 1, 2, 3, 2, 2, 2), 2))
      println(topK(List(1,1,1,2,2,2,3,4,4,4,4),2))


  // Output : List(4,1)

}
