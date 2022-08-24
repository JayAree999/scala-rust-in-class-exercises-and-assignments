object TurnIt extends App {
  def transpose(A: List[List[Int]]): List[List[Int]] = A.head match {
    case Nil => Nil
    case _ => firstOfAll(A) :: transpose(tail(A))   // add what out helper outputs to transpose which is List(1,4) , here A.tail is next loop 4,5,6

  }
  def tail(A: List[List[Int]]) : List[List[Int]] = {
    if A.isEmpty then Nil
    else {
      A.head.tail ::  tail(A.tail)
    }
  }
  def firstOfAll(A: List[List[Int]]) : List[Int] = A match { // return List(1,4)
    case Nil => Nil
    case (h :: t) => h.head :: firstOfAll(t)    // h 1 2 3 t 4 5 6

  }
  //  }
  println(transpose(List(List(1,2,3),List(4,5,6))))

}
