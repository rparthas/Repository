package com.scala.spark

import org.apache.spark.sql.types.{StringType, StructType, TimestampType}
import org.apache.spark.sql.{SparkSession, functions}

class StreamingExample extends SparkJob {
  override def execute(spark: SparkSession): Unit = {

    import spark.sqlContext.implicits._

    val mobileSchema = new StructType()
      .add("id", StringType, false)
      .add("action", StringType, false)
      .add("ts", TimestampType, false)

    val mobileStreaming = spark.readStream.schema(mobileSchema)
      .json("data/mobile")

    val actionCountDF = mobileStreaming.groupBy(functions.window('ts, "10 minutes"),
      'action).count

    val mobileConsoleSQ = actionCountDF.writeStream
      .format("console").option("truncate", "false")
      .outputMode("complete")
      .start()

    mobileConsoleSQ.awaitTermination()

  }

  override def getJobName(): String = "StreamingExample"
}
