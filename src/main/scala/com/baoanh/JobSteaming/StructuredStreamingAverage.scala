package com.baoanh.JobSteaming
import org.apache.spark.sql._
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.streaming._
import com.baoanh.JobSteaming.Person
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.types.{StructField, StructType, StringType, LongType}

object StructuredStreamingAverage extends App with Context {
    val INPUT_DIRECTORY: String = System.getProperty("user.dir") + "/input_files"
    println(INPUT_DIRECTORY)
    //val ssc:StreamingContext = new StreamingContext(sc, Seconds(5))
    override def main(args: Array[String]): Unit = {
        //1. Define the in put data schema
        val personSchema = StructType(
            List(
                StructField("firstName", StringType, true),
                StructField("lastName", StringType, true),
                StructField("sex", StringType, true),
                StructField("age", LongType, true)
            )
        )
        //2. Create a Dataset representing the stream of input files
        val personStream = sparkSession
            .readStream
            .schema(personSchema)
            .json("/Volumes/DataSu/dev/Scala-learning-steps/input_files")
        personStream.printSchema()
        //3. Create a temporary table so we can use SQL query
        personStream.createOrReplaceGlobalTempView("people")
        val sql: String = "SELECT AVG(age) as average_age, sex FROM people GROUP BY sex"

        val ageAverage = personStream
            .groupBy("sex").avg("age")
        ageAverage.printSchema()

        //4. Write the output of the query to the console
        val query: StreamingQuery = ageAverage.writeStream
            .outputMode("complete")
            .format("console")
            .start()

        query.awaitTermination()
    }
}
