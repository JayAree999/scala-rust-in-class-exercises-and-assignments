object Thesaurus extends App {

  val defaultEncoding = "ISO8859-1"

  def load(filename: String): Map[String, List[String]] = {
    val bufferedSource = scala.io.Source.fromFile(filename)
    val text = bufferedSource.getLines()
    text.next // Encoding

    def lineIterator(lineIt: Iterator[String], accMap: Map[String, List[String]]): Map[String, List[String]] = {
      if lineIt.hasNext then // hood | 1
        lineIterator(lineIt, accMap ++ splitNum(lineIt.next, lineIt))
      else
        bufferedSource.close()
        accMap
    }

    def mapping(numberOfLines: Int, it: Iterator[String], key: String, accList: List[String]): Map[String, List[String]] = {
      if numberOfLines == 0 then Map(key.->(accList))
      else {
        mapping(numberOfLines - 1, it, key, accList ++ it.next.split('|').tail.toList)
      }
    }


    def splitNum(line: String, it: Iterator[String]): Map[String, List[String]] = { // hood | 1 in here and return all its synonyms
      val Array(key, num) = line.split('|') // split hood | 1 => Array(hood,1)
      // map hood with  list of its neighbours
      mapping(num.toInt, it, key, List[String]())

    }

    lineIterator(text, Map[String, List[String]]())

  }

  //  println(parentPath(parent,"vague",List("vague")))
  def linkage(thesaurusFile: String): String => String => Option[List[String]] =
    val data = load(thesaurusFile)
    val nbrs = (key: String) => data.getOrElse(key, Nil).toSet // nbrs(node : String) 

    // get path from Key 
    def keyPath(bfsMap: Map[String, String], end: String, result: List[String]): Option[List[String]] = {
      if bfsMap.isEmpty then Nil
      val word: Option[String] = bfsMap.get(end)
      if word.isEmpty then return None
      if word.get.equals(end) then
        Some(result.reverse)
      else {
        keyPath(bfsMap, word.get, result :+ word.get)
      }
    }

    val list = (key: String) => (end: String) => keyPath(GraphBFS.bfs(nbrs, key)._1, end, List(end))
    return list

  val findLinks = linkage("C:\\Users\\Jay\\Desktop\\a2-starter\\a2-starter\\thesaurus_db.txt")
  val f = findLinks("clear")
  println(f("vague")) // compute a path from 'clear' to 'vague', based on the bfs outcome.
}
