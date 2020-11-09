package com.baoanh.JobSteaming
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

trait Context {
    lazy val conf = new SparkConf()
                                .setAppName("Job Streaming")
                                .setMaster("local[*]")
    lazy val sparkSession = SparkSession
                                                .builder()
                                                .config(conf=conf)
                                                .getOrCreate()

    lazy val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
}
