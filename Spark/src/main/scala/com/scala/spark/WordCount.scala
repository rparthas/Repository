package com.scala.spark

import org.apache.spark.sql.SparkSession

class WordCount extends SparkJob {
  override def execute(spark: SparkSession): Unit = {
    val textFiles = spark.sparkContext.textFile("hdfs://namenode:8020/input.txt")
    val words = textFiles.flatMap(line => line.split(" "))
    val wordTuples = words.map(word => (word, 1))
    val wordCounts = wordTuples.reduceByKey(_ + _).sortBy(a => a._1)
    wordCounts.saveAsTextFile("hdfs://namenode:8020/output")
  }

  override def getJobName(): String = "WordCount"

  override def getSparkMaster(): String = "spark://spark-master:7077"
}
