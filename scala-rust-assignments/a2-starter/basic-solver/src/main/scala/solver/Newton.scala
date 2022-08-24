package solver

object Newton extends App{

  // your implementation of the Newton method that takes a function f: Double => Double
  // and its derivative df: Double => Double  (take note of the types),
  // and computes a root of f using the Newton's method with the given 
  // guess: Double starting value

  def solve(f: Double => Double, df: Double => Double, 
            guess: Double = 1.0): Option[Double] = {
      try {
        val x = guess - f(guess)/df(guess)
        return Some(x)

      } catch {
        case _: Throwable => None
      }

    }

  def newtonJunior(f: Double => Double, df: Double => Double,
                   guess: Double): Double =
  {

    if math.abs(guess- Newton.solve(f,df,guess).get) < 0.0000000001 then return  Newton.solve(f,df,guess).get else newtonJunior(f, df,  Newton.solve(f,df,guess).get)
  }



}
