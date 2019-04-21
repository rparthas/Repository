package com.scala.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import scala.util.Random

class SparkQLFuncs extends SparkJob {
  override def execute(sc: SparkContext): Unit = {

    val spark = SparkSession
      .builder()
      .config(sc.getConf)
      .getOrCreate()

    val sqlContext = spark.sqlContext
    import sqlContext.implicits._

    val rdd = sc.parallelize(1 to 10).map(x => (x, Random.nextInt(100) * x))
    val kvDF = rdd.toDF("key", "value")
    kvDF.show()
    kvDF.printSchema()

  }

  override def getJobName(): String = "SparkQlFuncs"
}
