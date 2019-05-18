package com.baoanh.LearnSPARK
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkConx {
  var conf = new SparkConf()
  conf.setAppName("First App")
  conf.setMaster("local[*]")
  var sc = new SparkContext(conf)
  
}