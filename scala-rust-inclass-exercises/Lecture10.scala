object Lecture10 extends App {

  def odd(n: Int): LazyList[Int] = n #:: odd(n+1)
  def oddForm(n: Int) = odd(n) filter (i =>(i % 2 != 0) && oneOh(i))

  def oneOh(n: Int) : Boolean = n.toString.toList.count(_ == '1')%2 != 0
  oddForm(1).take(10).foreach(println)
  def recurrence: LazyList[Int] = {
    def next(n1: Int, n2: Int): LazyList[Int] = {
      val f = 2*n1+3*n2

      f #:: next(n2, f)
    }
    next(2,3)

  }

  recurrence.take(5).foreach(println)

  }
