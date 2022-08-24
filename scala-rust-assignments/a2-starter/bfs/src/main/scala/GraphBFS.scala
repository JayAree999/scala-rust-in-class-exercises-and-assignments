object GraphBFS extends App {

  def bfs[V](nbrs: V => Set[V], src: V) = {

    def loopNeighboursMap(currentFrontierNodes: Set[V] , neighbours: Set[V], parent: Map[V,V]) : Map[V,V] = { // update map
      if neighbours.isEmpty then return parent
      else if !parent.contains(neighbours.head) then // neighbours.head = A t = Set(D) // update Map
       val currentFrontierNode = currentFrontierNodes.head
       loopNeighboursMap(currentFrontierNodes, neighbours.tail, parent ++ Map(neighbours.head -> currentFrontierNode))
      else loopNeighboursMap(currentFrontierNodes, neighbours.tail, parent)

    }
    def nextFrontier(currentFrontierNode: V , neighbours: Set[V], parent: Map[V,V], setFromThisNode: Set[V]) : Set[V] = { // update frontier
      if neighbours.isEmpty then return setFromThisNode
      else if !parent.contains(neighbours.head) then
        nextFrontier(currentFrontierNode, neighbours.tail, parent, setFromThisNode + neighbours.head)
      else nextFrontier(currentFrontierNode, neighbours.tail, parent,setFromThisNode)
    }


    def loopSets(setOfFrontiers : Set[V], parent: Map[V,V], accSet : Set[V]) : (Set[V],Map[V,V]) = { // B,C

      if setOfFrontiers.isEmpty then return (accSet,parent)
      else {
        val currentFrontierNode = setOfFrontiers.head
        val newParentMap = loopNeighboursMap(setOfFrontiers, nbrs(currentFrontierNode),parent) // update map every time when new neighbour is found
        val newFrontier = nextFrontier(currentFrontierNode, nbrs(currentFrontierNode),parent,Set[V]()) // store neighbours in Set until the end of this frontier
        loopSets(setOfFrontiers.tail, newParentMap,accSet ++ newFrontier)
      }

    }
    def expand(frontier: Set[V], parent: Map[V, V]) = {
        loopSets(frontier, parent,Set[V]())
    }

    def iterate(frontier: Set[V],
                parent: Map[V, V],
                distance: Map[V, Int], d: Int
               ): (Map[V, V], Map[V, Int]) =
      if frontier.isEmpty then
        (parent, distance)
      else {
        val (frontier_, parent_) = expand(frontier, parent)
        val distance_ = distance ++ frontier.foldLeft(Map[V, Int]()){(map, V) => map + (V -> d)} // add distance to each Vertex in each iteration , use map as acc during loop
        iterate(frontier_, parent_, distance_, d + 1)
      }

    iterate(Set(src), Map(src -> src), Map(), 0)

  }

//  def nbrs(node: String): Set[String] = {
//    if node.equals("A") then Set("B", "C")
//    else if node.equals("B") then Set("A", "D")
//    else if node.equals("C") then Set("A", "E")
//    else if node.equals("D") then Set("B", "F")
//    else if node.equals("E") then Set("C", "F")
//    else if node.equals("F") then Set("D", "E")
//    else Set()
//  }
//
//  println(bfs(nbrs,"A"))
}
