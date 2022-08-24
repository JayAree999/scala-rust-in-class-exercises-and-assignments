import scala.annotation.tailrec

object DateUtil extends App {
  type Date = (Int, Int, Int)

  def isOlder(x: Date, y: Date): Boolean = {
    if x == y then false
    else
      if x._3 > y._3 then true
      else if x._2 > y._2 then true
      else if x._1 > y._1 then true
      else false
  }

  def numberInMonth(xs: List[Date], month: Int): Int = xs.count(_._2 == month) // List(Date(1,6,2000),Date(3,6,2000)) = should return 2

  def numberInMonths(xs: List[Date], months: List[Int]): Int = {
    if months.isEmpty then 0
    else {
      numberInMonth(xs,months.head) + numberInMonths(xs,months.tail)
    }
  }

  def datesInMonth(xs: List[Date], month: Int): List[Date] = xs match { // List(Date(1,6,2000),Date(3,6,2000),Date(3,4,2000),Date(3,5,2000)), month =  6 => 3
    case Nil => Nil
    case _ => if xs.head._2 == month then xs.head :: datesInMonth(xs.tail, month) else datesInMonth(xs.tail, month)

  }

  def datesInMonths(xs: List[Date], months: List[Int]): List[Date] = {
    if months.isEmpty then Nil
    else {
      datesInMonth(xs, months.head) ::: datesInMonths(xs, months.tail)
    }
  }
    //  List(Date(1,6,2000),Date(4,6,2000),Date(3,6,2000),Date(3,4,2000),Date(3,5,2000)) , List(4,5) => 2

  def dateToString(d: Date): String = { //  input Date(1,6,2000) ,  return August-10-2017

    val months = "January, February, March, April, May, June, July, August, September, October, November, December".split(",").map(_.trim)
    months(d._2-1)+"-"+d._1+"-"+d._3
  }

  def is_leap(year: Int): Boolean = {
    if year % 400 == 0 then
      return true
    else if  year % 100 == 0 then
      return false
    else if  year % 4 == 0 then
      return true
    else false
  }

  def whatMonth(n: Int, yr: Int): Int = { // whatMonth(330,2000) => 1

    val monthDays = List(31, 28, 31, 30, 31, 30, 31,
      31, 30, 31, 30, 31)

    def counter(n: Int, yr: Int,sum: Int,lst: List[Int], month: Int) : Int = {
      if sum>=n then return month
      else if is_leap(yr) && lst.head == 28 then counter(n,yr,sum+29,lst.tail,month)
      else counter(n,yr,sum+lst.head,lst.tail,month+1)

    }
    counter(n,yr,0,monthDays,1)

  }

  def oldest(dates: List[Date]): Option[Date] = {
    if dates.isEmpty then None
    else
      def helper(dates: List[Date]) : Date = {
        if dates.tail.isEmpty then dates.head
        else {
          val last = helper(dates.tail)
          val first = dates.head
        if isOlder(first,last) then first
        else last
        }
      }
      Some(helper(dates))
  }

  def isReasonableDate(d: Date): Boolean = {
    def daysInMonth(month: Int, year: Int): Int = {
      if month == 2 && is_leap(year) then 29
      else {
        val daysInMonth = List(31, 28, 31, 30, 31, 30, 31,
          31, 30, 31, 30, 31)
        def helper(currentMonth : Int, months: List[Int]): Int = {
          if currentMonth == 1 then months.head
          else helper(currentMonth-1, months.tail)
        }
        helper(month,daysInMonth)

      }
    }
    if d._3 > 0 && d._2 > 0 && d._2 < 13 && d._1 > 0 && d._1 < 32 then
      if daysInMonth(d._2,d._3) < d._1 then false else true
    else false
  }

  println(isOlder((21,5,2022), (20,5,2002)))
  println(numberInMonth(List((1,2,2011), (2,12,2011), (4,2,2011)),3))
  println(numberInMonths( List((1,2,2011), (2,12,2011), (4,2,2011)) , List(2, 12)))
  println(datesInMonth( List((1,2,2011), (2,12,2011), (4,2,2011), (2,3,2011), (10,2,2011)), 2))
  println(datesInMonths( List((1,2,2011), (2,12,2011), (4,2,2011), (2,3,2011), (10,2,2011), (4,8,2011), (2,7,2011), (10,9,2011)), List(2,7)))
  println(dateToString(12,5,2014))
  println(whatMonth(60,2024))
  println(oldest( List((1,1,2001), (51,10,2015), (12,4,2001))))
  println(isReasonableDate( (29,2,4) ))
}
