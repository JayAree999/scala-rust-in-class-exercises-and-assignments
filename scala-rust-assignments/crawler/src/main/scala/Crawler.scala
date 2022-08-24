import org.jsoup.*
import org.jsoup.Connection.Base
import org.jsoup.select.Elements

import scala.io.Source
import java.net.URL
import scala.annotation.tailrec
import scala.collection.mutable
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.*
import scala.concurrent.duration.*
import scala.util.{Failure, Success}
import scala.jdk.CollectionConverters.*
object Crawler {

  sealed case class WebStats(
    // the total number of (unique) files found
    numFiles: Int,
    // the total number of (unique) file extensions (.jpg is different from .jpeg)
    numExts: Int,
    // a map storing the total number of files for each extension.
    extCounts: Map[String, Int],
    // the total number of words in all html files combined, excluding
    // all html tags, attributes and html comments.
    totalWordCount: Long
  )
  var visited: Set[String] = Set()
  var extPathSet: Set[String] = Set()
  var extCount: Map[String, Int] = Map()
  var totalWord: Int = 0

  def crawlBFS(basePath: Any, base: URL) = {

    val p = Promise[Set[Any]]
    try {
      var urlPath: String = basePath.toString

      visited += urlPath

      if urlPath.split('/').takeRight(1).mkString.contains('.') then extPathSet += urlPath.substring(urlPath.lastIndexOf("."),urlPath.length)

      val doc = Jsoup.connect(urlPath).ignoreContentType(true).ignoreHttpErrors(true).get()

      val divs = List()

      import scala.collection.mutable.ListBuffer

      val links = doc.select("a[href]")
      val pic = doc.select("img[src]")
      val script = doc.select("script[src]")
      val link = doc.select("link[href]")
      val picLink = pic.asScala.map(_.attr("abs:src"))
      val scLink = script.asScala.map(_.attr("abs:src"))
      val Link = links.asScala.map(_.attr("abs:href"))
      val impLink = link.asScala.map(_.attr("abs:href"))
      val saveAll = Link ++ picLink ++ impLink ++ scLink
      var frontier: Set[String] = Set()

      var fruits = new ListBuffer[Elements]()
      fruits += links
      fruits += pic
      fruits += script
      fruits += link

      def countWords(es : ListBuffer[Elements]) = {
        es.foreach(e => totalWord += e.text().trim.split("\\s+").filter(_.nonEmpty).map(_.replaceAll("[^A-Za-z0-9]", "")).toList.size)
      }
      countWords(fruits)


      saveAll.toSet.foreach {
        x => {

          val lastPathArray = x.split('/').takeRight(1).mkString
          if lastPathArray.contains('#') then frontier += x.substring(0, x.lastIndexOf("#"))

          else if lastPathArray.contains('?') then frontier += x.substring(0, x.lastIndexOf("?"))
          else if lastPathArray.contains('\\') then frontier += x.substring(0, x.lastIndexOf("\\"))

          else if x.charAt(x.length - 1).equals('/') then {
            val xs = x + "index.html"
            frontier += xs
          }
          else frontier += x
        }
      }
      p.success(frontier.asInstanceOf[Set[Any]])
    } catch {
      case e: Exception => p.failure(e)
    }
    p.future
  }

  // a2 stuffs
  def bfs(src: String, host: URL): Unit = {

    var frontier = Await.result(crawlBFS(src, host), Duration.Inf)

    while (frontier.nonEmpty) {
      val frontier_ : Set[Any] = expand(frontier, host)

      frontier = frontier_

    }
  }

  // a2 stuffs
  def expand(frontier: Set[Any], host: URL): Set[Any] = {

    frontier.flatMap {
      x => {
        if x.toString.contains(host.getHost) && !visited.contains(x.toString) then Await.result(crawlBFS(x, host), Duration.Inf) else Set.empty
      }
    }
  }

  def crawlForStats(basePath: String): WebStats = {
    val yeet: Unit = bfs(basePath, URL(basePath))

    extPathSet.map{
      extension => extCount += (extension, visited.count(_.contains(extension)))
    }

    WebStats(visited.size, extPathSet.size, extCount, totalWord)
  }

  def main(args: Array[String]) = {
    val sampleBasePath: String = "https://cs.muic.mahidol.ac.th/courses/ooc/api/"
    println(crawlForStats(sampleBasePath))
  }



}
