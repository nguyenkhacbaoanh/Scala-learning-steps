package com.baoanh.LearnSPARK
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkConx {
  def main(args : Array[String]) = {
  //  set a configuration of Spark
    var conf = new SparkConf()
    conf.setAppName("First App")
    conf.setMaster("local")
  //  create a spark context
    var sc = new SparkContext(conf)
  //  read csv file by sc
    val path_dir:String = System.getProperty("user.dir")
    val path_file = path_dir + "/data/stations.csv"
    /**
     * if there is a problem issue like 
     * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 10582
     *  -----
     * add the follow dependency in the pom.xml
     *  -----
     * <dependency>
     *    <groupId>com.thoughtworks.paranamer</groupId>
     *    <artifactId>paranamer</artifactId>
     *    <version>2.8</version>
     * </dependency>
     */
    val stations = sc.textFile(path_file, 2)
    println("First row od stations.csv: %s".format(stations.first()))
    println("row total of stations.csv: %d".format(stations.count()))
    
    /**
     * Output:
     * First row od stations.csv: 2,San Jose Diridon Caltrain Station,37.329732,-121.901782,27,San Jose,8/6/2013
     * row total of stations.csv: 70
     */
  }
}