
//Record is a named tuple
//type Person = (String,Double,Int,String)
//def processPerson(p: Person) : Person = {
//
//}
//// similar to Java class - attributes
//case class Person(name: String, height: Double, age: Int, add : String)
//val p1 = Person("Thana", 22.15,15,"n/a")
//
//// trait - similar to Interface
//trait Expr
// case class Const(n: Double) extends Expr
// case class Prod(e1 : Expr, e2 : Expr) extends  Expr
// case class Sum(e1: Expr, e2 : Expr) extends Expr
// case class Negate(e : Expr) extends Expr
//trait Rank similar to Enum
//case object Jack extends Rank
//case object Queen extends Rank
//case object King extends Rank
//case object Ace extends Rank
//case class Num(


//Pattern Matching
//def isConstant(e: Expr) : Boolean = e match{
//  // match any case class above
//  case Const(_) => true // Constant(type : Double)
//  case _ => false
//}
//def eval(e: Expr) : Double = e match{
//  // return single number after eval
//  case Const(n) => n
//  case Prod(e1,e2) => eval(e1) * eval(e2)
//  case Sum(e1,e2) => eval(e1) + eval(e2)
//  case Negate(e) => -eval(e)

//}
// List(3,1) , List(4,2) => List(1,2,3,4)
//def merge(xs: List[Int], ys: List[Int]) : List[Int] = (xs,ys) match{
//  case (Nil,_)  => ys
//  case (_,Nil) => xs
//  case (x::xt, y::yt) => // (x::xt) = head :: tail <= xs.head :: xs.tail
//    if x <= y then x::merge(xt,ys) else y::merge(xs,yt) // if x.head <= y.head then x:: merge else y
//
//}
//In-class exercise

object Lecture5 extends App {
  def zip(x : List[Int], y: List[Int]) : List[(Int, Int)] = (x,y) match {
    case (Nil,Nil) => Nil
    case (x::xt, y::yt) => (x,y) :: zip(xt,yt)
  }
  def unzip(zipped : List[(Int, Int)]) : (List[Int], List[Int]) = zipped match {
    case Nil => (Nil,Nil)
    case _ =>
      val (x,y)::t = zipped
      (x:: unzip(zipped.tail)._1 ,y :: unzip(zipped.tail)._1 )
    // (3,6) then next loop (2,1) , want (3,2 . (6,1


  }
  def merge(xs: List[Int], ys: List[Int]) : List[Int] = (xs,ys) match {
    case (Nil, _) => ys
    case (_, Nil) => xs
    case (x :: xt, y :: yt) => // (x::xt) = head :: tail <= xs.head :: xs.tail
      if x <= y then x :: merge(xt, ys) else y :: merge(xs, yt) // if x.head <= y.head then x:: merge else y

  }
  println(zip(List(3,2,5), List(6,1,9)))
  println(unzip(List((3, 6), (2,1), (5,9))))
  println(merge(List(1,3),List(2,4)))
}
//takes, for example,
//(List(3,2,5), List(6,1,9))
//and returns List((3,6), (2,1), (5,9)). Hint: you can pattern match on tuples. case (Nil, Nil)
//is valid.
//• def unzip(zipped : List[(Int, Int)]) : (List[Int], List[Int]) takes, for example,
//3
//(List((3, 6), (2,1), (5,9))
//  and returns (List(3, 2, 5), List(6, 1, 9)).
//• def merge(xs: List[Int], ys: List[Int]): List[Int]