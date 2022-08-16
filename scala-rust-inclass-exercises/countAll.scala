
object Lecture14{
  def exists[T](fut: Future[T], p: T => Boolean): Future[Boolean] = {
    val promise = Promise[Boolean]

    if (fut.isCompleted){
      fut.map(x => p(x)) // predicate runs on the outcome x yield true?

      promise.success(true)

    }
    promise.future // the future with that promise
  }
  implicit val executionContext: ExecutionContext = ExecutionContext.global
  def listOfFiles(dirName: String): Vector[File] = {
    val dir = java.io.File(dirName)
    if dir.exists && dir.isDirectory then {
      dir.listFiles()
        .filter(_.isFile)
        .toVector
    } else
      Vector()
  }
  def allLines(name: File): Future[Iterator[String]] = Future {
    Source.fromFile(name, "ISO-8859-1")
      .getLines()
  }
  def tokenCounts(lines: Iterator[String]): Iterator[Int] =
    lines.map(_.split("\\W+").length)
  def wordCount(fileName: File): Future[Int] =
    allLines(fileName)
      .map(tokenCounts)
      .map(_.sum)
  @main
  def countAll(dir: String) = {
    val files = listOfFiles(dir)
    val filenames = files.map(_.getName)
    val counts = files.map(wordCount)
    val lineCount = Future.sequence(files.map(allLines(_)))
    val answerFut = Future.sequence(counts)
      .map(filenames.zip(_))
    val answer = Await.result(answerFut, Duration.Inf)
      .map((name, count) => s"${name}: ${count}")
    println(answer.mkString("\n"))
  }
}

