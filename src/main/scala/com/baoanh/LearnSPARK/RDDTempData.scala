package com.baoanh.LearnSPARK

object RDDTempData extends App with Context {
  /*
   * This scripts to work with RDD and return the same result from ReadDatafrmText
   * The advantage of RDD, Google know all about it, so feel free to search it
   */
  // import the spark context from class Context that created
  import com.baoanh.LearnSPARK.Context
  // import case class TempData
  import com.baoanh.BigDataAnalyst.ReadDatafromText.TempData
  
  def main(args: Array[String]):Unit= {
    val pathFile:String = "file://" + System.getProperty("user.dir") + "/data/tempdata.txt"
    val data = sparkSession
                .read
                .textFile(pathFile)
                .rdd
                .filter(!_.contains("Day ")) //drop the header
                .filter(!_.contains(".")) // drop missing value with dot symbol
                .map{ line =>
      val p = line.split(",")
      TempData(p(0).toInt, p(1).toInt, p(2).toInt, p(3).toString, p(4).toInt, p(5).toDouble, 
               p(6).toDouble, p(7).toDouble, p(8).toDouble, p(9).toDouble)
    }
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
    println(f"Day hot in year: ${hotDays.collect.mkString(", ")}") //HotDays is RDD type, we need to collect, dont do that when you have a huge data to collect
    println("--------------------------------------")
    // An other ways to find hot days
    val hotday1 = data.max()(Ordering.by(_.tmax))
    println(f"Hot day 1 in year: ${hotday1}")
    val hotday2 = data.reduce((d1, d2) => 
                                    if (d1.tmax <= d2.tmax) d2 else d1)
    println(f"Hot day 2 in year: ${hotday2}")
    println("--------------------------------------")
    //Count rainy day
    val rainyCount = data.filter(_.precip >= 1.0).count()
    println(f"Number of rainy days: ${rainyCount}. There are ${rainyCount*100.0/data.count} percent.")
    println("--------------------------------------")
    // Using aggregate function to calculate more one operation with dataset
    val (rainySumTemp, rainyCount2) = data.aggregate(0.0 -> 0)({case ((sum, cnt), td) => 
        if (td.precip < 1.0) (sum, cnt) else (sum+td.tmax, cnt+1)
      }, {case ((s1,c1),(s2,c2)) => 
        (s1+s2,c1+c2)
      })
//    println(s"Average Rainy temp using aggregate method is ${rainySumTemp/rainyCount2}")
    // Using flatMap to get the same result
    /*
     * flatMap method take each elements in array to apply with a function and return these elements of this result
     */
    val rainyTemp = data.flatMap(td => if (td.precip < 1.0) Seq.empty else Seq(td.tmax))
    println(s"Average Rainy temp using flatMap method is ${rainyTemp.sum/rainyTemp.count}")
    println("--------------------------------------")
    // Groupby method
    val groupMonth = data.groupBy(_.month)
    val TempHighAvgByMonth = groupMonth.map { case (m, days) => 
      m -> days.foldLeft(0.0)((sum, td) => 
        sum + td.tmax)/days.size
    }
    println(f"Temp Avg by Month: ")
    TempHighAvgByMonth.collect foreach println
    println("--------------------------------------")
    // println(f"Temp Avg by Month by Vector ${TempAvgByMonth.toSeq.sortBy(_._1)}")
    TempHighAvgByMonth.sortBy(_._1) foreach {
      case (m, avgtemp) => println(f"Month $m have average $avgtemp temperature")
    }
    /*
     * Double RDD Function
     */
    println(f"Stdev of High Monthly Temperature: ${data.map(_.tmax).stdev()}")
    println(f"Stdev of Low Monthly Temperature: ${data.map(_.tmin).stdev()}")
    println(f"Stdev of Average Monthly Temperature: ${data.map(_.tave).stdev()}")
    println("--------------------------------------")
    // keyByYear
    val keyedByYear = data.map(td => td.year -> td)
    val averageTempsByYear = keyedByYear.aggregateByKey(0.0 -> 0)({ case ((sum, cnt), td) =>
      (sum+td.tmax, cnt+1)
    }, { case ((s1, c1), (s2, c2)) => (s1+s2, c1+c2) })
    averageTempsByYear.take(10) foreach println
  } 
}