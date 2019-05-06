package com.scala.spark

import org.apache.spark.sql.types.{DoubleType, StringType, StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

class StreamingExample extends SparkJob {
  override def execute(spark: SparkSession): Unit = {

    //    processStream(fixedWindow(spark))
    processStream(slidingWindow(spark))

  }

  private def processStream(actionCountDF: DataFrame) = {
    val mobileConsoleSQ = actionCountDF.writeStream
      .format("console").option("truncate", "false")
      .outputMode("complete")
      .start()

    mobileConsoleSQ.awaitTermination()
  }

  private def fixedWindow(spark: SparkSession) = {
    import spark.sqlContext.implicits._

    val mobileSchema = new StructType()
      .add("id", StringType, false)
      .add("action", StringType, false)
      .add("ts", TimestampType, false)

    val mobileStreaming = spark.readStream.schema(mobileSchema)
      .json("data/iot")

    val actionCountDF = mobileStreaming.groupBy(functions.window('ts, "10 minutes"),
      'action).count

    actionCountDF
  }

  private def slidingWindow(spark: SparkSession) = {
    import spark.sqlContext.implicits._

    val iotDataSchema = new StructType().add("rack", StringType, false)
      .add("temperature", DoubleType, false)
      .add("ts", TimestampType, false)

    val mobileStreaming = spark.readStream.schema(iotDataSchema)
      .json("data/iot")

    val iotDF = mobileStreaming.groupBy(functions.window('ts, "10 minutes",
      "5 minutes"),'rack).agg(functions.avg('temperature).as("avg_temp"))

    iotDF
  }

  override def getJobName(): String = "StreamingExample"
}
