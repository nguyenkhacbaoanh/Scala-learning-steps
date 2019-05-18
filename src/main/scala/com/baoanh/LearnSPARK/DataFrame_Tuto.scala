package com.baoanh.LearnSPARK
import com.baoanh.LearnSPARK.Context

object DataFrame_Tuto extends App with Context {
  def main(args: Array[String]) = {
    // csv file path
    val path_dir:String = System.getProperty("user.dir")
    val path_file = path_dir + "/data/stations.csv"
    // Station Data Frame
    val stationDF = sparkSession
                    .read
                    .option("header", false)
                    .option("inferSchema", true) //detected data type from csv
                    .csv(path_file)
                    .toDF("id","Station_name","latitude","longtitude","available","zone","date")
    // data frame                
    stationDF.show(10)
    // DataFrame Schema
    stationDF.printSchema()
    /**
     * root
     *  |-- id: integer (nullable = true)
     *  |-- Station_name: string (nullable = true)
     *  |-- latitude: double (nullable = true)
     *  |-- longtitude: double (nullable = true)
     *  |-- available: integer (nullable = true)
     *  |-- zone: string (nullable = true)
     *  |-- date: string (nullable = true)
     */
    
    // Select function to choose the column to show
    stationDF.select("Station_name", "available").show(10)
    
    // filter data
    stationDF.filter("available >= 15").show(5) // condition expression or SQL like: "Station_name LIKE 'S%'"
    
    //data frame query: count()
    var avai_gte_15: BigInt = stationDF.filter("available >= 15").count()
    println(s"Number of available greater than 15: ${avai_gte_15}")
    
    // SQL IN Clause
    stationDF.filter("available in (15, 19)").show(10)
    
    // GROUP BY: count number of zone
    stationDF.groupBy("zone").count().show()
    
    // AGGREGATE
    stationDF.groupBy("zone").agg(("available","sum"),("zone","count")).show()
    
    //Stop session
    sparkSession.stop()
  }
}