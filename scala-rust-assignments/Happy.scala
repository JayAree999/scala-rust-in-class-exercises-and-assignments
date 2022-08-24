object Happy extends App {
  // TODO: write these functions!
  def sumOfDigitsSquared(n: Int): Int = {

    if n==0 then 0
    else {
      val digit : Int = n%10 // 145%10 = 5
      // 145/10/10/10%10 <-- Google

      digit*digit + sumOfDigitsSquared(n/10)
    }
  }
  def isHappy(n: Int): Boolean = {

    val nextHappy: Int = sumOfDigitsSquared(n)
    if nextHappy==1 then true
    else if nextHappy==4 then false
    else isHappy(nextHappy)

  }

  def kThHappy(k: Int): Int ={
//    def happy2Helper(n: Int)
  // n => 0 to k
    def decrementHelper(counter : Int, value: Int): Int = {

    if counter == 0 then return value
    else
      if isHappy(value) then decrementHelper(counter - 1, value + 1)
      else decrementHelper(counter, value + 1)

  }

    val n : Int = decrementHelper(k,1) // start at 1
    if n >= k then n-1
    else {
      kThHappy(n)
    }
  }

  println(kThHappy(19))

}
