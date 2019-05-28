package BigDataAnalyst

object ReadDatafromText {
  import scala.io.Source.fromFile
  import java.lang.NumberFormatException
  
  case class TempData( day: Int, doy: Int, month: Int, state_id: String, year: Int, precip: Double, 
                      snow: Double, tave: Double, tmax: Double, tmin: Double)
  // for the exception nergative number
  def convertToInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case ex: NumberFormatException => -1
    }
  }
                      
  def main(args: Array[String]): Unit = {
    val path_dir = System.getProperty("user.dir")
    val file_source = path_dir + "/data/tempdata.txt"
    val source = fromFile(file_source)
    val lines = source.getLines().drop(1) //drop first line, it is the header in text file
    val data = lines.filterNot(_.contains(".")).map{ line =>
      val p = line.split(",") // List String
      TempData(p(0).toInt, p(1).toInt, p(2).toInt, p(3).toString, p(4).toInt, p(5).toDouble, 
               p(6).toDouble, p(7).toDouble, p(8).toDouble, p(9).toDouble)
    }.toArray
    source.close()
    println("First 5 row in data: ")
    data.take(5) foreach println
    println("--------------------------------------")
    // find the temperature max in this data
    val maxTemp = data.map( _.tmax ).max
    println(f"The highest temp in dataset: $maxTemp")
    println("--------------------------------------")
    //find the list of hotday thank to result from maxTemp that is found above
    val hotDays = data.filter(_.tmax == maxTemp).map( _.doy )
    // hotDays.foreach(day => println(f"Day hot in year: $day"))
    println(f"Day hot in year: ${hotDays.mkString(", ")}")
    println("--------------------------------------")
    // An other ways to find hot days
    val hotday1 = data.maxBy(_.tmax)
    println(f"Hot day 1 in year: ${hotday1}")
    val hotday2 = data.reduceLeft((d1, d2) => 
                                    if (d1.tmax >= d2.tmax) d1 else d2)
    println(f"Hot day 2 in year: ${hotday2}")
    //Count rainy day
    val rainyCount = data.count(_.precip >= 1.0)
    println(f"Number of rainy days: ${rainyCount}. There are ${rainyCount*100.0/data.length} percent.")
    println("--------------------------------------")
    // Using aggregate function to calculate more one operation with dataset
    val (rainySumTemp, rainyCount2) = data.par.aggregate(0.0 -> 0)({case ((sum, cnt), td) => 
        if (td.precip < 1.0) (sum, cnt) else (sum+td.tmax, cnt+1)
      }, {case ((s1,c1),(s2,c2)) => 
        (s1+s2,c1+c2)
      })
    println(s"Average Rainy temp using aggregate method is ${rainySumTemp/rainyCount2}")
    // Using flatMap to get the same result
    /*
     * flatMap method take each elements in array to apply with a function and return these elements of this result
     */
    val rainyTemp = data.flatMap(td => if (td.precip < 1.0) Seq.empty else Seq(td.tmax))
    println(s"Average Rainy temp using flatMap method is ${rainyTemp.sum/rainyTemp.length}")
    println("--------------------------------------")
    // Groupby method
    val groupMonth = data.groupBy(_.month)
    val TempAvgByMonth = groupMonth.map { case (m, days) => 
      m -> days.foldLeft(0.0)((sum, td) => 
        sum + td.tmax)/days.length
    }
    println(f"Temp Avg by Month ${TempAvgByMonth}")
    // println(f"Temp Avg by Month by Vector ${TempAvgByMonth.toSeq.sortBy(_._1)}")
    TempAvgByMonth.toSeq.sortBy(_._1) foreach {
      case (m, avgtemp) => println(f"Month $m have average $avgtemp temperature")
    }
  }
}