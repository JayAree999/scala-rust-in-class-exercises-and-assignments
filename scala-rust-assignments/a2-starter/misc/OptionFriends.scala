object OptionFriends extends App {
  def lookup(xs: List[(String, String)], key: String): Option[String] = xs match {
    case Nil => None
    case _ => if xs.head._1 == key then Some(xs.head._2) else lookup(xs.tail, key)
  }


  def resolve(userIdFromLoginName: String => Option[String],
              majorFromUserId: String => Option[String],
              divisionFromMajor: String => Option[String],
              averageScoreFromDivision: String => Option[Double],
              loginName: String): Double ={


    val user = Some(loginName).map(userIdFromLoginName).getOrElse[Option[String]](Some(""))
    val major = user.map(majorFromUserId).getOrElse[Option[String]](Some(""))
    val division = major.map(divisionFromMajor).getOrElse[Option[String]](Some(""))
    val avg = division.map(averageScoreFromDivision).getOrElse[Option[Double]](Some(0.0))
    avg.get


  }

  val tuple = List(("a","1"),("b","2"))
  println(lookup(tuple,"b"))

  val userIdFromLoginName = (x: String) => lookup(List(("jj123","1"),("jjgg","2")),x)
  val majorFromUserId = (x: String) => lookup(List(("1","CS"),("2","FINANCE")),x)
  val divisionFromMajor = (x: String) => lookup(List(("FINANCE","BUSINESS"),("CS","SCIENCE")),x)
  val averageScoreFromDivision = (x: String) => if (x == "SCIENCE") Option(90.0) else Option(80.0)
  println(resolve(userIdFromLoginName,majorFromUserId,divisionFromMajor,averageScoreFromDivision,"jjgg"))

}
