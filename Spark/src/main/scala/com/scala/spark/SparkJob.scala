package com.scala.spark

import org.apache.spark.sql.SparkSession

trait SparkJob {
  def execute(spark: SparkSession)

  def getJobName(): String

  def getSparkMaster(): String = "local"
}
