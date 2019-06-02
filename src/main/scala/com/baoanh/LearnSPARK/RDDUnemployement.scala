package com.baoanh.LearnSPARK
case class Area(code: String, text: String)
case class Series(id: String, area: String, measure: String, title: String)
case class LaData(id: String, year: Int, period: Int, value: Double)
object RDDUnemployement extends App with Context {
  def main(args: Array[String]):Unit = {
    import com.baoanh.LearnSPARK.Context
    val path_dir: String = System.getProperty("user.dir")
      // Area data
    val area = sc.textFile(path_dir + "/data/la.area")
                .filter(!_.contains("area_type_code")) // drop the header text
                .map { line =>
                  val p = line.split("\t").map(_.trim)
                  Area(p(1), p(2))
                }.cache() //cache method or persist method for memory data to the next using
    area.take(10) foreach println
    // Series data
    val series = sc.textFile(path_dir + "/data/la.series")
                  .filter(!_.contains("area_type_code")) // drop the header text
                  .map{ line =>
                    val p = line.split("\t").map(_.trim)
                    Series(p(0), p(2), p(3), p(6))
                  }.cache()
    series.take(10) foreach println
    // LaData 30 Minnesota
    val laData = sc.textFile(path_dir + "/data/la.data.30.Minnesota")
                  .filter(!_.contains("series_id")) // drop the header text
                  .map{ line =>
                    val p = line.split("\t").map(_.trim)
                    LaData(p(0), p(1).toInt, p(2).drop(1).toInt, p(3).toDouble)
                  }.cache()
    laData.take(10) foreach println
    /*
     * join data processing
     */
    val rates = laData.filter(_.id.endsWith("03"))
    // Pair RDD
     val decadeGroups = rates.map(d => (d.id, d.year/10) -> d.value)
     decadeGroups.take(5) foreach println
//     calculate group average
     val decadeAvg = decadeGroups.aggregateByKey(0.0 -> 0)({ case ((sum, cnt),v) =>
         (sum + v, cnt + 1)
      }, {
      case ((s1, c1),(s2, c2)) => (s1 + s2, c1 + c2)}).map({case (x,y) => (x,y._1/y._2)})
     decadeAvg.foreach(println)
    
     val maxDecade = decadeAvg.map{case ((id, dec), av) => id -> (dec*10, av)}. //*10 to return year
                   reduceByKey{ case ((d1, a1),(d2, a2)) => if (d1 >= d2) (d1,a1) else (d2,a2)}
    
     val pairSeries = series.map(p => p.id -> p.title)
     // join two PairRDD with the same Key (id)
     val joinDecadeSeries = pairSeries.join(maxDecade)
     
    sc.stop()
  }
}