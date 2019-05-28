package com.baoanh.LearnSPARK.CheateSheet
import java.util.Date
case class Trips(tripid: String, duration: Int, startdate: Date, startstation: String, 
                startterminal: String, enddate: Date, endstation: String, endterminal: String, 
                bike: Int, subtype: String, zipcode: String)

object ReadData {
  import scala.io.Source.fromFile
  import java.lang.ArrayIndexOutOfBoundsException
  def main(args: Array[String]): Unit = {
    val dirname: String = System.getProperty("user.dir")
    // println(dirname)
    val data_source = dirname + "/data/trips.csv"
    val source = fromFile(data_source)
    val lines = source.getLines().drop(1) //drop header
    /*
     * for the matrix data:
     * we need to split line by "," and give the data type for each columnes
     */
    // This data have some of not a value
    // lines.filterNot(_.contains("nil")).filterNot(_.endsWith(",")): this line of code to filter or ignore same kind of nan
    // but i want to replace this nan by some value
    val data = lines.map { line =>
                              try {
                                val p = line.split(",")
//                              println(p.length)
                                val startdate = new Date(p(2))
                                val enddate = new Date(p(5))
                                Trips(p(0).toString, p(1).toInt, startdate, p(3).toString, 
                                      p(4).toString, enddate, p(6).toString, p(7).toString, 
                                      p(8).toInt, p(9).toString, p(10).toString)
                              } catch { // there are many of case with missing value
                                case ex: ArrayIndexOutOfBoundsException => {
                                   // println(line)
                                  // for all of missing value replace by NaN or any value if you want like, average ...
                                  val li = line.concat("NaN")
                                  val p = li.split(",")
  //                              println(p.length)
                                  val startdate = new Date(p(2))
                                  val enddate = new Date(p(5))
                                  Trips(p(0).toString, p(1).toInt, startdate, p(3).toString, 
                                        p(4).toString, enddate, p(6).toString, p(7).toString, 
                                        p(8).toInt, p(9).toString, p(10).toString)
                                }  
                              }
                           }.toArray
     source.close()
     
    data.take(5) foreach println
  }
}