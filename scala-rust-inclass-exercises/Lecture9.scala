object Lecture9 extends App{
    trait Expr { // Scala's "interface" can have code in it.
      def +(that: Expr) = Sum(this, that)
      def *(that: Expr) = Prod(this, that)
      def unary_- = Negate(this)
      def eval(ctx: Map[String, Double]): Double
      def **(that : Expr) = Pow(this,that) //e1**e2
    }
    case class Var(name: String) extends Expr {
      def eval(ctx: Map[String, Double]) = ctx(name)
    }
    case class Constant(n: Double) extends Expr {
      def eval(ctx: Map[String, Double]) = n
    }
    case class Negate(e: Expr) extends Expr {
      def eval(ctx: Map[String, Double]) = -e.eval(ctx)
    }
    case class Sum(e1: Expr, e2: Expr) extends Expr {
      def eval(ctx: Map[String, Double]) = e1.eval(ctx) + e2.eval(ctx)
    }
    case class Prod(e1: Expr, e2: Expr) extends Expr {
      def eval(ctx: Map[String, Double]) = e1.eval(ctx) * e2.eval(ctx)
    }
  case class Pow (e1: Expr, e2: Expr) extends Expr {
    def eval(ctx: Map[String, Double]) = Math.pow(e1.eval(ctx),e2.eval(ctx))
  }

  trait BST {
    def ++(that: Int) = insert(that,this)
  }
  case object Empty extends BST
  case class Node(left : BST, key: Int,right : BST) extends BST
  def insert(e: Int, rt: BST) : BST = rt match {
    case Empty => Node(Empty,e,Empty)
    case Node(l,k,r) =>
      if e<k then Node(insert(e,l),k,r) else if e == k then rt
      else Node(l,k,insert(e,r))
  }
}

