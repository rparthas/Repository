package com.scala.spark

import org.apache.spark.SparkContext

class RDDTest() extends SparkJob {
  override def execute(sc: SparkContext): Unit = {
    val rdd1 = sc.parallelize(Array(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(Array(1, 6, 7, 8))
    rdd1.union(rdd2).distinct().sortBy(a => a).collect().foreach(a => println("Number:" + a))
    rdd1.intersection(rdd2).sortBy(a => a).collect().foreach(a => println("Number:" + a))

    val words = sc.parallelize(List("The amazing thing about spark is that it is very simple to learn")).flatMap(l => l.split(" ")).map(w => w.toLowerCase)
    val stopWords = sc.parallelize(List("the it is to that")).flatMap(l => l.split(" "))
    words.subtract(stopWords).sortBy(a => a).collect().foreach(a => println("Word:" + a))

  }

  override def getJobName(): String = "RDDTest"
}
