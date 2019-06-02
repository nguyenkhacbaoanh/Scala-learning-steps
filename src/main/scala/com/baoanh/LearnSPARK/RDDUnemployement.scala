package com.baoanh.LearnSPARK
case class Area(code: String, text: String)
case class Series(id: String, area: String, measure: String, title: String)
case class LaData(id: String, year: Int, period: Int, value: Double)
object RDDUnemployement extends App with Context {
  import com.baoanh.LearnSPARK.Context
  val path_dir: String = System.getProperty("user.dir")
  def main(args: Array[String]) = {
    // Area data
    val area = sparkContext
                .textFile(path_dir + "/data/la.area")
                .filter(!_.contains("area_type_code")) // drop the header text
                .map { line =>
                  val p = line.split("\t").map(_.trim)
                  Area(p(1), p(2))
                }.cache()
    area.take(10) foreach println
    // Series data
    val series = sparkContext
                  .textFile(path_dir + "/data/la.series")
                  .filter(!_.contains("area_type_code")) // drop the header text
                  .map{ line =>
                    val p = line.split("\t").map(_.trim)
                    Series(p(0), p(2), p(3), p(6))
                  }.cache()
    series.take(10) foreach println
    // LaData 30 Minnesota
    val laData = sparkContext
                  .textFile(path_dir + "/data/la.data.30.Minnesota")
                  .filter(!_.contains("series_id")) // drop the header text
                  .map{ line =>
                    val p = line.split("\t").map(_.trim)
                    LaData(p(0), p(1).toInt, p(2).drop(1).toInt, p(3).toDouble)
                  }.cache()
    laData.take(10) foreach println
    
    sparkContext.stop()
  }
}