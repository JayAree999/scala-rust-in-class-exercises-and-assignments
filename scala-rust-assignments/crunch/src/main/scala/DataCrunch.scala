import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object DataCrunch {

  trait DataProvider {
    def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit
  }


  object LoremIpsum extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit = {
      val lorem =
        """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Cras nec sagittis justo. Nullam dignissim ultricies velit a tempus.
        Aenean pharetra semper elit eu luctus. Fusce maximus lacus eget magna
        ultricies, ac suscipit justo lobortis. Nullam pellentesque lectus
        at tellus gravida rhoncus. Nam augue tortor, rutrum et eleifend id,
        luctus ut turpis. Vivamus nec sodales augue.

        Suspendisse non erat diam. Mauris hendrerit neque at sem laoreet
          vehicula. Sed aliquam urna a efficitur tincidunt. In non purus
        tincidunt, molestie velit vulputate, mollis nisl. Pellentesque
        rhoncus molestie bibendum. Etiam sit amet felis a orci fermentum
        tempor. Duis ante lacus, luctus ac scelerisque eget, viverra ut felis."""
      onSuccess(lorem.split("\n"))
    }
  }

  object FailedSample extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
                     onFailure: () => Unit): Unit = {
      onFailure()
    }
  }

  // This returns a DataProvider that feeds the "consumer" all the lines from a
  // file indicated by filename.
  def FileSource(filename: String): DataProvider = new DataProvider {
    override def get(onSuccess: Seq[String] => Unit, onFailure: () => Unit): Unit = {
      try {
        val lines = io.Source.fromFile(filename)
          .getLines
          .toVector
        onSuccess(lines)
      }
      catch {
        case _: Throwable => onFailure()
      }
    }
  }

  def dataProviderFuture(dp: DataProvider): Future[Seq[String]] = {
    val p = Promise[Seq[String]]
    try{
      dp.get(line => {p.success(line)}, funcOnFailure)

    } catch {
      case e: Exception => p.failure(e)
    }
    def funcOnFailure() = Exception("failed")
    val fileProvider: DataProvider = FileSource("/bigdata/lorem.txt")
    p.future
  }

  def highestFreq(linesFut: Future[Seq[String]]): Future[(String, Double)] = {
    val eachLineWords = linesFut.map(l => l.flatMap(w => w.trim.split("\\s+")))

    val totalWord: Future[Int] = wordCount(linesFut)

    val f:  Future[Map[String,Int]] = eachLineWords.map(countFreq)

    val max: Future[(String,Int)] = f.map(_.maxBy(_._2)) // a, 3 get max

    val ratio: Future[Double] = max.flatMap{
      wordCountF => totalWord.map{
        base => wordCountF._2.toDouble/base.toDouble
      }
    }
    val res = (max.map(_._1),ratio) // a, 0.02

    // in handout
    val ans = for(
      val1 <- res._1;
      val2 <- res._2
    ) yield (val1,val2)
    ans
  }

  def countFreq(eachLineWords: Seq[String]): Map[String, Int] = {
    val result = Promise[Map[String, Int]]
    val uniqueWords: Set[String] = eachLineWords.toSet
    val f: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map.empty
    for(w <- uniqueWords){
      f.put(w,eachLineWords.count(_.equals(w)))
    }
    f.toMap
  }

  // from in-class
  def tokenCounts(lines: Seq[String]): Seq[Int] =
    lines.map(_.split("\\s+").length)

  def wordCount(linesFut: Future[Seq[String]]): Future[Int] =
    linesFut
      .map(tokenCounts)
      .map(_.sum)

  def main(args: Array[String]) = {
    // Example of how DataProvider is typically used. Comment out the block of code
    // below so it doesn't print some random garbage.
    def funcOnSuccess(lines: Seq[String]) = lines.foreach(println(_))
    def funcOnFailure() = println("Failed")
    val sampleProvider = LoremIpsum
    val result = dataProviderFuture(sampleProvider)
    println(result)
    val ans2= highestFreq(result)
    Await.result(ans2,Duration.Inf)
    println(ans2)



  }

}
