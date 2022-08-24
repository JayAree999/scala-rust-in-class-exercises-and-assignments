object Maze extends App{
  case class xy(x : Int, y: Int)

  def scan(maze: Vector[String], currentPosition : xy, nbr :Map[xy, Set[xy]], start : xy, end : xy) : (Map[xy, Set[xy]],xy,xy) = {

    val lstIdx : List[xy] = List(xy(1,0),xy(-1,0),xy(0,1),xy(0,-1)) // up down left righht
    val moveIdx = xy(currentPosition.x+1,currentPosition.y)
    if currentPosition.y > maze.size-2 then  // maze.size - 2 because want only inside edge
      (nbr,start,end)
    else if currentPosition.x > maze.head.length-1 then
      scan(maze, xy(0,currentPosition.y+1), nbr, start, end) // right most edge
    else if maze(currentPosition.y).charAt(currentPosition.x).toString.equals(" ") then // case " " set Current Position
      scan(maze, moveIdx, nbr + (currentPosition.->(neighbour(maze,currentPosition,lstIdx))), start, end)
    else if maze(currentPosition.y).charAt(currentPosition.x).toString.equals("s") then // case "s" set Current Position
      scan(maze, moveIdx, nbr + currentPosition.->(neighbour(maze,currentPosition,lstIdx)), currentPosition, end)

    else if maze(currentPosition.y).charAt(currentPosition.x).toString.equals("e") then // case "e" set Current Position
      scan(maze, moveIdx, nbr + currentPosition.->(neighbour(maze,currentPosition,lstIdx)), start, currentPosition)

    else scan(maze, moveIdx, nbr, start, end)

  }

  def neighbour(maze: Vector[String], XY: xy, directions: List[xy]): Set[xy] = directions.size match {
    case 0 => Set.empty
    case 4 => if !maze(XY.y).charAt(XY.x + 1).toLower.toString.equals("x") then neighbour(maze, XY, directions.tail) +  xy(XY.x + 1 , XY.y)
    else neighbour(maze, XY, directions.tail)

    case 3 => if !maze(XY.y).charAt(XY.x - 1).toLower.toString.equals("x") then neighbour(maze, XY, directions.tail) +  xy(XY.x - 1, XY.y)
    else neighbour(maze, XY, directions.tail)

    case 2 => if !maze(XY.y + 1).charAt(XY.x).toLower.toString.equals("x") then neighbour(maze, XY, directions.tail) +  xy(XY.x, XY.y + 1)
    else neighbour(maze, XY, directions.tail)

    case 1 => if !maze(XY.y - 1).charAt(XY.x).toLower.toString.equals("x") then neighbour(maze, XY, directions.tail) +  xy(XY.x, XY.y - 1)
    else neighbour(maze, XY, directions.tail)

  }

  def keyPath(bfsShortestMap: Map[xy,xy], result: List[xy], start: xy, des: xy, defaultStart: xy, defaultBfsShortestMap: Map[xy,xy]): List[xy] = {
      if bfsShortestMap.isEmpty then defaultStart :: result // nothing in BFS Map
      else if des.equals(start) then defaultStart :: result // e to s is done
      else {

      val end: xy = defaultBfsShortestMap.getOrElse(des, Maze.xy(0,0))
      keyPath(bfsShortestMap.tail,  result :+ end, start, end,defaultStart,defaultBfsShortestMap)
      }

  }

  def solveMaze(maze: Vector[String]): Option[String] = Some(helper(xyToList,""))

    def whatDirection(prev: xy, current:xy)  : String = {
      if current.x - prev.x == 1 then "r"
      else if current.x - prev.x == -1 then  "l"
      else if current.y - prev.y == 1 then  "d"
      else if current.y - prev.y == -1 then  "u"
      else ""
    }

    def helper(shortestList : List[xy],  accString: String) : String =  {
      if shortestList.tail.isEmpty then accString
      else
        val prev = shortestList.head
        val current = shortestList.tail.head
        helper(shortestList.tail,accString + whatDirection(prev,current))
    }

    val maze: Vector[String] = Vector(
      "xxxxxxxxxxxxxxxxxx",
      "x   x       x   ex",
      "x   x    x  x xxxx",
      "x        x  x    x",
      "xs  x    x       x",
      "xxxxxxxxxxxxxxxxxx")

  /* My answer look like this | = up/down,  _ = right/left

      "xxxxxxxxxxxxxxxxxxxxxxxx",
      "x   x     ___  x --- ex",
      "x   x     |x | x|  xxxx",
      "x   |-----|x | x|     x",
      "xs--x      x |__|     x",
      "xxxxxxxxxxxxxxxxxxxxxxxx")
   */

  val (mapDirections, start,end)  =
      scan(maze, xy(0, 0), Map[xy, Set[xy]](), start = xy(0, 0), end = xy(0, 0))

    def path(startXY  : xy) : Set[xy] = {
      mapDirections.getOrElse(startXY, Set[xy]())
    }
    val graphBfs = GraphBFS.bfs(path, end)
    val xyToList = keyPath(graphBfs._1,List.empty,end,start,start,graphBfs._1)

    println(solveMaze(maze))

}
