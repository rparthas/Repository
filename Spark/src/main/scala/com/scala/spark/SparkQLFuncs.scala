package com.scala.spark

import org.apache.spark.sql.SparkSession

import scala.util.Random

class SparkQLFuncs extends SparkJob {
  override def execute(spark: SparkSession) = {
    val sqlContext = spark.sqlContext
    import sqlContext.implicits._

    val rdd = spark.sparkContext.parallelize(1 to 10).map(x => (x, Random.nextInt(100) * x))
    val kvDF = rdd.toDF("key", "value")
    kvDF.show()
    kvDF.printSchema()

  }

  override def getJobName(): String = "SparkQlFuncs"
}
