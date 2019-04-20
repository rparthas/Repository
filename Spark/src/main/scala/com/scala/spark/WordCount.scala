package com.scala.spark

import org.apache.spark.SparkContext

class WordCount extends SparkJob {
  override def execute(sc:SparkContext): Unit = {
    val textFiles = sc.textFile("hdfs://namenode:8020/input.txt")
        val words = textFiles.flatMap(line => line.split(" "))
        val wordTuples = words.map(word => (word, 1))
        val wordCounts = wordTuples.reduceByKey(_ + _).sortBy(a => a._1)
        wordCounts.saveAsTextFile("hdfs://namenode:8020/output")
  }

  override def getJobName(): String = "WordCount"
}
