object Roman extends App {
  // TODO: implement this
  def toRoman(n: Int): String = {
    val i = List(1000,900,500,400,100,90,50,40,10,9,5,4,1)
    val romanChars = List("I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M")

    val res0: List[(String,Int)] = romanChars.reverse.zip(i)

   def toRomanNumerals( number: Int, digits: List[(String, Int)] ) : String ={

     if digits.isEmpty then ""
     else
       digits.head._1 * (number / digits.head._2) + toRomanNumerals( number % digits.head._2, digits.tail)
    }
    toRomanNumerals(n, res0)
    // M - 1000
    // CM - 900
    // D - 500
    // CD - 400
    // C - 100
    // XC - 90
    // L - 50
    // XL - 40
    // X - 10
    // IX - 9
    // V - 5
    // IV - 4
    // I - 1

  }
  println(toRoman(44))
}
