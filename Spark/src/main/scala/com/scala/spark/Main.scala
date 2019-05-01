package com.scala.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Main {

  def main(args: Array[String]): Unit = {

//    val sparkJob = new WordCount()
//    val sparkJob = new RDDFuncs()
//    val sparkJob = new Movie()
//    val sparkJob = new SparkQLFuncs()
//    val sparkJob = new WindowFuncs()
    val sparkJob = new StreamingExample()


    val config = new SparkConf()
      .setMaster(sparkJob.getSparkMaster())
      .setAppName(sparkJob.getJobName())

    val spark = SparkSession
      .builder()
      .config(config)
      .getOrCreate()

    sparkJob.execute(spark)

  }

}



