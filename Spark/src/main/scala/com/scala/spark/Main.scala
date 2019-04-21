package com.scala.spark
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]): Unit = {

//    val sparkJob = new WordCount()
//    val sparkJob = new RDDTest()
//    val sparkJob = new Movie()
    val sparkJob = new SparkQLFuncs()
    val config = new SparkConf()
      .setMaster(sparkJob.getSparkMaster())
      .setAppName(sparkJob.getJobName())
    val sc = new SparkContext(config)
    sparkJob.execute(sc)

  }

}



