package com.scala.spark
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]): Unit = {

//    val sparkJob = new WordCount()
    val sparkJob = new RDDTest()
    val config = new SparkConf()
      .setMaster("local")
//      .setMaster("spark://spark-master:7077")
      .setAppName(sparkJob.getJobName())
    val sc = new SparkContext(config)
    sparkJob.execute(sc)

  }

}



