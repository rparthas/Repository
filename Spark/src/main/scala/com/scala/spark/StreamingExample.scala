package com.scala.spark

import com.scala.spark.model._
import org.apache.spark.sql.streaming.{GroupStateTimeout, OutputMode}
import org.apache.spark.sql.types.{StringType, StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

class StreamingExample extends SparkJob {
  override def execute(spark: SparkSession): Unit = {

    //    processStream(fixedWindow(spark))
    //    processStream(slidingWindow(spark))
    //    processStream(slidingWindowWithWaterMark(spark), "update")
    //    sessionProcessing(spark)
    //    sessionWithTimeout(spark)
    deduplication(spark)

  }

  private def processStream(actionCountDF: DataFrame, outputMode: String = "complete") = {
    val mobileConsoleSQ = actionCountDF.writeStream
      .format("console").option("truncate", "false")
      .outputMode(outputMode)
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

    val mobileStreaming = spark.readStream.schema(Rack.iotDataSchema)
      .json("data/iot")

    val iotDF = mobileStreaming.groupBy(functions.window('ts, "10 minutes",
      "5 minutes"), 'rack).agg(functions.avg('temperature).as("avg_temp"))

    iotDF
  }

  private def slidingWindowWithWaterMark(spark: SparkSession) = {
    import spark.sqlContext.implicits._

    val mobileStreaming = spark.readStream.schema(Rack.iotDataSchema)
      .json("data/iot")

    val iotDF = mobileStreaming.
      withWatermark("ts", "10 minutes")
      .groupBy(functions.window('ts, "10 minutes",
        "5 minutes"), 'rack)
      .agg(functions.avg('temperature).as("avg_temp"))

    iotDF
  }

  def sessionProcessing(spark: SparkSession): Unit = {
    import spark.sqlContext.implicits._

    val iotSSDF = spark.readStream.schema(Rack.iotDataSchema).json("data/iot")
    val iotPatternDF = iotSSDF.as[RackInfo]
      .groupByKey(_.rack)
      .mapGroupsWithState[RackState, RackState](GroupStateTimeout.NoTimeout())(Rack.updateAcrossAllRackStatus)

    val iotPatternSQ = iotPatternDF.writeStream
      .format("console")
      .outputMode("update")
      .start()
    iotPatternSQ.awaitTermination()

  }

  def sessionWithTimeout(spark: SparkSession) = {
    import spark.sqlContext.implicits._

    val userActivityDF = spark.readStream.schema(User.userActivitySchema).json("data/user")
    val userActivityDS = userActivityDF.withWatermark("ts", "30 minutes").as[UserActivity]

    val userSessionDs = userActivityDS.groupByKey(_.user)
      .flatMapGroupsWithState[UserSessionState, UserSessionInfo](OutputMode.Append,
      GroupStateTimeout.EventTimeTimeout)(User.updateAcrossAllUserActivities)

    val userSessionSQ = userSessionDs.writeStream
      .format("console")
      .option("truncate", false)
      .outputMode("append")
      .start()
    userSessionSQ.awaitTermination()

  }

  def deduplication(spark: SparkSession) = {
    val mobileDataSchema = new StructType().add("id", StringType, false)
      .add("action", StringType, false)
      .add("ts", TimestampType, false)
    val mobileDupSSDF = spark.readStream.schema(mobileDataSchema)
      .json("data")
    val windowCountDupDF = mobileDupSSDF.withWatermark("ts", "10 minutes")
      .dropDuplicates("id", "ts")
      .groupBy("id").count
    val mobileMemoryDupSQ = windowCountDupDF.writeStream
      .format("console")
      .option("truncate", "false")
      .outputMode("update")
      .start()
    mobileMemoryDupSQ.awaitTermination()
  }

  override def getJobName(): String = "StreamingExample"
}
