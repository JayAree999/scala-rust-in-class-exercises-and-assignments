import scala.io.Source
import scala.concurrent.Future
import java.util.concurrent.ConcurrentSkipListSet
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable
import scala.language.postfixOps

object TopK {

  // similar to first question
  def countWords(words: String): mutable.Map[String, Int] = {
    val counter = mutable.Map.empty[String, Int].withDefaultValue(0)
    for (w<- words.split("[ ,!.]+")) {
      counter(w.toLowerCase) += 1
    }
    counter
  }


  def topKWords(k: Int)(fileSpec: String): Vector[(String, Int)] = {
    implicit val executionContext: ExecutionContext = ExecutionContext.global
    // read line into list of line
    var lstOfLines= List[String]()
    val reader = Source.fromFile(fileSpec)
    for (l<- reader.getLines) {
      if l.nonEmpty then
      lstOfLines = l :: lstOfLines
    }
    reader.close

    val handles = lstOfLines.map(line => Future{
      countWords(line)
    })

    // merge all answer into one
    var res = mutable.Map.empty[String, Int].withDefaultValue(0)

    val sequence = Future.sequence(handles)
      .map(resL=> resL.map {
      resL =>
        res = res.map{ case (key,value) =>
          key -> (value + res.getOrElse(key,0))
        }
    })

    // wait for answer
    Await.result(sequence, Duration.Inf)


    val res2 = res.toVector.sortBy(tupleAns => (-tupleAns._2,tupleAns._1))

    res2.take(k)

  }

  def main(args: Array[String]): Unit = {
    val t = topKWords(10)("/Users/Jay/Desktop/a3-starter/topk/src/main/scala/text.txt")
    println(t)
  }
}