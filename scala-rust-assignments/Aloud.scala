object Aloud extends App {
  // TODO: Implement me
  def readAloud(xs: List[Int]): List[Int] = {

    def loop(remaining: List[Int], currentValue: Int, currentCount: Int, acc: List[Int]): List[Int] =
        {
        if remaining.isEmpty then return currentCount :: currentValue :: acc
        else if remaining.head == currentValue then
          loop(
            remaining = remaining.tail,
            currentValue,
            currentCount + 1,
            acc
          )
          else
            loop(
              remaining = remaining.tail,
              currentValue = remaining.head,
              currentCount = 1,
              currentCount  :: currentValue :: acc
            )
      }
      if xs.isEmpty then return List()
      else
        loop(
          remaining = xs.tail,
          currentValue = xs.head,
          currentCount = 1,
          acc = List.empty
        )
    }
  println(readAloud(List(1,1,1)))
 
}

//https://stackoverflow.com/questions/37645958/what-does-case-y-ys-mean