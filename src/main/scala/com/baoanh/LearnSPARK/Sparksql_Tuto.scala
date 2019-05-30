package com.baoanh.LearnSPARK

object Sparksql_Tuto extends App with Context {
  import com.baoanh.LearnSPARK.Context
  def main(args: Array[String]) {
    val path_dir:String = System.getProperty("user.dir")
    val tripsDF = sparkSession
                .read
                .option("header", true)
                .option("inferSchema", true)
                .csv(path_dir + "/data/trips.csv")
                .toDF()
                
    // Register temp table from DF
    // Instead of operate directly in DF we register in temporary table                
    tripsDF.createOrReplaceTempView("so_tripsDF")
    
    // List all tables in Spark's catalogs:
    sparkSession.catalog.listTables().show()
    
    // Using SQL like to see all tables in Spark's catalogs: sql() function in SparkSession
    sparkSession.sql("SHOW TABLES").show(); // Note: don't need ";" in the end of query, it dont work :D
    
    // other query test
    sparkSession.sql("SELECT `Start Station`, `End Station`, Duration FROM so_tripsdf LIMIT 10")
                .show()  
  }
}