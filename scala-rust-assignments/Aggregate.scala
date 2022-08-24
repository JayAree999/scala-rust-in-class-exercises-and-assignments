object Aggregate extends App {
  // TODO: implement these functions for real!
  def myMin(p: Double, q: Double, r: Double): Double = math.min(p, math.min(q,r))

  def myMean(p: Double, q: Double, r: Double): Double = (1.toDouble/3)*(p+q+r)
  def myMed(p: Double, q: Double, r: Double): Double =  {

    if (p<q && p>r) || (p<r && p>q) then // 4<1  false , 4>5 false | 4<5 true && 4>1 true then return 4
      // need to check 1 4 5 and 5 4 1
      p
    else if (q<p && q>r) || (q<r && q>p)  then // 1<4 false , 1>5 true
      q
    else
      r

  }

  println(myMin(1,2,3))
  println(myMean(3,7,4))
  println(myMed(4,1,5))
  println(myMed(13,5.0,12))
}
