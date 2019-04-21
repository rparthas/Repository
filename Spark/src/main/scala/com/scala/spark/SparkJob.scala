package com.scala.spark

import org.apache.spark.SparkContext

trait SparkJob{
  def execute(sc:SparkContext)
  def getJobName():String
  def getSparkMaster():String = "local"
}
