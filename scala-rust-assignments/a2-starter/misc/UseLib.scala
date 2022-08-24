object UseLib extends App {
  def onlyBeginsWithLower(xs: Vector[String]): Vector[String] = xs.filter(e => e.toLowerCase().head == e.head)


  def longestString(xs: Vector[String]): Option[String] = Some(xs.maxBy(e => e.length))

  def longestLowercase(xs: Vector[String]): Option[String] = longestString(onlyBeginsWithLower(xs))


  println(onlyBeginsWithLower(Vector("TEST", "HELLO","worldE")))
  println(longestString(Vector("TEST", "HELLO","worldT")))
  println(longestLowercase(Vector("TEST", "HELLO","worldE")))


}
