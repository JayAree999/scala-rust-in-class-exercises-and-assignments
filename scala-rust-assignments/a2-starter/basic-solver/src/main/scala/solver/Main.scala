package solver

object Solver extends App{
  // solves expString == 0 for the variable in varName with an initial guess
  // specified. We'll assume that the given expression has a root.

  def solve(expString: String, varName: String, guess: Double): Double = {
    val ex = Parser(expString).get
    val f = (double: Double) => Process.eval(ex, Map(varName -> double))
    val ex2 = Process.differentiate(ex,varName)
    val df = (double: Double) => Process.eval(ex2, Map(varName -> double)) // Process.differentiate(f,varName) is Expression
    // TODO: complete the implementation. This will construct the 
    // appropriate functions and call Newton.solve
    Newton.newtonJunior(f,df,guess)
    // <- remove me when you're done
  }
  println(solve("2^(x) - 4.0", "x", 1.0))

}
