import scala.io.Source
import scala.util.Using

object PrimeCount  extends App{
  // omit isPrime and countSeq
  class Counter {
    private var count: Int = 0

    def increment() = this.synchronized {
      this.count += 1
    }

    def get: Int = this.synchronized {
      count
    }

    def incrementBy(int: Int) = this.synchronized {
      this.count += int
  }
  }

  val NUM_GROUPS = 4

  def isPrime(n: Int): Boolean = {
    val sqrtN = math.sqrt(n).toInt
    n > 1 && (2 to sqrtN).forall { d => n % d != 0 }
  }

  def timed[A](name: String)(f: => A): (Double, A) = {
    println(s"Running $name ...");
    Console.flush()
    3
    val start = System.nanoTime
    val res = f
    val stop = System.nanoTime
    println("Done");
    Console.flush()
    ((stop - start) / 1e9, res)
  }

  def countSeq(r: Range): Int = r.count(isPrime)



  def countWords(fileSpec: String): Int = {
    val total: Counter = new Counter
    val groups = Source.fromFile(fileSpec, "ISO-8859-1").getLines().grouped(2) // using standard ASCII encoding

    val handles = groups.map(group => new Thread {
      override def run(): Unit = {

        println(Thread.currentThread.getName + "  " + group)
        group.foreach { line =>
          val wordsPerLine = line.split("\\s+").count(_.nonEmpty)
          total.incrementBy(wordsPerLine)
        }
      }
    })

    handles.foreach(_.start())
    handles.foreach(_.join())
    total.get


  }

  countWords("C:\\Users\\Jay\\Desktop\\test.txt")


}

