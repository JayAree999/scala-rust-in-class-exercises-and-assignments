package solver

object Process extends App{
  // gives a "pretty-print" string form of the expression
  def stringify(e: Expression): String = e match {
    case Constant(c) => c.toString
    case Var(name) => name
    case Sum(l, r) => stringify(l) + " + " + stringify(r)
    case Prod(l @ Sum(_, _), r @ Sum(_, _)) => "(" + stringify(l) + ") * (" + stringify(r) + ")"
    case Prod(l @ Sum(_, _), r) => "(" + stringify(l) + ") * " + stringify(r)
    case Prod(l, r @ Sum(_, _)) => stringify(l) + " * (" + stringify(r) + ")"
    case Prod(l, r) => stringify(l) + " * " + stringify(r)
    case Power(b, e) => stringify(b) + "^" + stringify(e)
  }

  // evaluates a given expression e: Expression using
  // the variable settings in varAssn: Map[String, Double],
  // returning the evaluation result as a Double.

  // Example: eval(e, Map("x" -> 4.0)) evaluates the expression 
  // with the variable "x" set to 4.0.
  def eval(e: Expression, varAssn: Map[String, Double]): Double = e match {
    case Constant(c) => c
    case Var(e) => varAssn(e)
    case Prod(e1,e2) => eval(e1,varAssn) * eval(e2,varAssn)
    case Sum(e1,e2) => eval(e1,varAssn) + eval(e2,varAssn)
    case Power(e1,e2) => Math.pow(eval(e1,varAssn), eval(e2,varAssn))

  }

  // symbolically differentiates an expression e: Expression with
  // respect to the variable varName: String

  def differentiate(e: Expression, varName: String): Expression = e match {

    case Constant(e) => Constant(0)
    case Var(a) => if Var(a).equals(a) then return Constant(1) else return e //
    case Sum(e1,e2) => Sum(differentiate(e1,varName),differentiate(e2,varName)) //  ดิฟของตัวหน้า + ดิฟของตัวหลัง
    case Prod(e1,e2) =>
      Sum(Prod(e1,differentiate(e2,varName)),// หน้าดิฟหลัง
        //+
      Prod(e2,differentiate(e1,varName)))// หลังดิฟหน้า
    case Power(base,h) =>
      val c  = differentiate(base,varName)
      val turnToDouble = math.log(eval(base,Map(varName -> 1)))
      val doubleToExpression= Constant(turnToDouble)

      if c.equals(Constant(0)) then Prod(doubleToExpression, Power(base,h))
      else {
      Prod(
      Prod(h, // ตัวกำลัง
        Power(base, // f(x)
          Sum(h,Constant(-1)))) // h-1

        /**
         * h*[f (x)]^(h−1) then
         * */

      , differentiate(base,varName))
        /**
         *   times df(x)/dx
         */

      }

  }

  // forms a new expression that simplifies the given expression e: Expression
  // the goal of this function is produce an expression that is easier to
  // evaluate and/or differentiate.  If there's a canonical form you'd like to
  // follow, use this function to put an expression in this form.
  // you may leave this function blank if can't find any use. 
  def simplify(e: Expression): Expression = {

    ???
  }

}
