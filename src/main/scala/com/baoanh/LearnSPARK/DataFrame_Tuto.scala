package com.baoanh.LearnSPARK

object DataFrame_Tuto extends App with Context {
  import com.baoanh.LearnSPARK.Context
  def main(args: Array[String]) = {
    // csv file path
    val path_dir:String = "file://" + System.getProperty("user.dir")
    val path_file = path_dir + "/data/stations.csv"
    // Station Data Frame
    val stationDF = sparkSession
                    .read
                    .option("header", false)
                    .option("inferSchema", true) //detected data type from csv
                    .csv(path_file)
                    .toDF("Id","Station_name","Latitude","Longtitude","Terminal","Zone","Date")
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
    // stationDF.select("Station_name", "available").show(10)
    
    // filter data
    // stationDF.filter("available >= 15").show(5) // condition expression or SQL like: "Station_name LIKE 'S%'"
    
    //data frame query: count()
    // var avai_gte_15: BigInt = stationDF.filter("available >= 15").count()
    // println(s"Number of available greater than 15: ${avai_gte_15}")
    
    // SQL IN Clause
    // stationDF.filter("available in (15, 19)").show(10)
    
    // GROUP BY: count number of zone
    // stationDF.groupBy("zone").count().show()
    
    // AGGREGATE
    // stationDF.groupBy("zone").agg(("available","sum"),("zone","count")).show()
    
    // Trips data
    // download from https://raw.githubusercontent.com/data-8/materials-x18/master/lec/x18/1/trip.csv
    val tripsDF = sparkSession
                .read
                .option("header", true)
                .option("inferSchema", true)
                .csv(path_dir + "/data/trips.csv")
                .toDF()
   
    // head of trips data
    tripsDF.show(10)      
    tripsDF.printSchema()
    /**
     * root
       |-- Trip ID: integer (nullable = true)
       |-- Duration: integer (nullable = true)
       |-- Start Date: string (nullable = true)
       |-- Start Station: string (nullable = true)
       |-- Start Terminal: integer (nullable = true)
       |-- End Date: string (nullable = true)
       |-- End Station: string (nullable = true)
       |-- End Terminal: integer (nullable = true)
       |-- Bike #: integer (nullable = true)
       |-- Subscriber Type: string (nullable = true)
       |-- Zip Code: string (nullable = true)
     */
    
    // join
    tripsDF.join(stationDF, // DF join with
                            stationDF("Station_name")===tripsDF("Start Station"), // criteria join
                            "left_outer") // kind of join
            .select("Trip ID","Zone","Date","Station_name")
            .show(10)
    
    //Stop session
    sparkSession.stop()
  }
}