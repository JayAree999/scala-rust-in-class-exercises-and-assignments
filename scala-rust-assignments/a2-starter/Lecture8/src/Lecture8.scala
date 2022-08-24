object Lecture8 extends App{

  enum RBColor:
    case Red, Black
  sealed trait RBTree[+T]
  case object Empty extends RBTree[Nothing]
  case class Node[T](
                      left: RBTree[T],
                      key: T,
                      color: RBColor,
                      right: RBTree[T]
                    ) extends RBTree[T]

  def isWellRed[K](rt: RBTree[K]) : Boolean =rt match {
    case Empty => true
    case Node(Node(_,_,RBColor.Red,_),_,RBColor.Red,_) => false
    case Node(Node(_,_,RBColor.Red,_),_,RBColor.Red,_) => false
    case _ => val Node(l,k,c,r) = rt
    isWellRed(l) && isWellRed(r)

  }
  def equalBlackHeight[K](rt: RBTree[K]) : Boolean ={
    def helper[K](rt : RBTree[K],black: Int) : Int = rt match {
      case Empty => black
      case Node(left,_,RBColor.Black,right) =>
        val leftHeight = helper(left,black+1)
        val rightHeight = helper(right,black+1)
        if leftHeight != rightHeight || leftHeight == -1 || rightHeight == -1 then -1
        else leftHeight
      case Node(left,_,RBColor.Red,right) =>
        val leftHeight = helper(left,black)
        val rightHeight = helper(right,black)
        if leftHeight != rightHeight || leftHeight == -1 || rightHeight == -1 then -1
        else leftHeight
    }
    helper(rt,0) != 1
  }
}
