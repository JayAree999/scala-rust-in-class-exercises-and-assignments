object Zombies extends App {

  /*Sources :
  //https://youtu.be/K4MQXjmxU8M
  */

  def countBad(hs: List[Int]): Int = {
    def mergeSort(lst: List[Int], count: Int ): (List[Int], Int) = lst match { // this will split the left till it contains a single element e.g 3,1,2,4 => 3,1 => 3 1
      case Nil => (lst,count) // if list contains single element or empty

      case h :: Nil => (lst,count) // if head is List()  = > List()
      // _ anything else
      case _ => val (l, r) = lst.splitAt(lst.length / 2)
        merge(mergeSort(l,count)._1, mergeSort(r,count)._1, mergeSort(l,count)._2 +mergeSort(r,count)._2)

    }

    // add 2 sorted list
    def merge(l: List[Int], r: List[Int], count:Int ): (List[Int],Int) = (l, r) match {
      case (Nil, _) => (r,count) // if left is empty return right
      case (_, Nil) => (l,count) // if right is empty return left
      case (h1 :: t1, h2 :: t2) => // split left to h1,t1 and right to h2,t2


        if (h1 > h2) (h1 :: merge(t1, r,count)._1, merge(t1, r,count)._2) // 3 > 4

        else (h2 :: merge(l, t2,count+l.length)._1,merge(l, t2,count+l.length)._2) // 3<4
        // h1-3 1-t1 4-h2 2-t2
        // 4 :: l (3 1) t2 2 => 4
        // 3 1 :: 2 =>  4 3
        // 1 :: 2 =>  4 3 2
        // :: 2 = > 4 3 2 1
    }
    mergeSort(hs, 0)._2

  }

  println(countBad(List(35, 22, 10)))
  println(countBad(List(3,1,4,2)))
  println(countBad(List(5,4,11,7)))
  println(countBad(List(1, 7, 22, 13, 25, 4, 10, 34, 16, 28, 19, 31)))

}


