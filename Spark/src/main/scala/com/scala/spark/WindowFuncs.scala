package com.scala.spark

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{SparkSession, functions}

class WindowFuncs extends SparkJob {
  override def execute(spark: SparkSession): Unit = {
    import spark.sqlContext.implicits._
    val txDataDF = Seq(("John", "2017-07-02", 13.35),
      ("John", "2017-07-06", 27.33),
      ("John", "2017-07-04", 21.72),
      ("Mary", "2017-07-07", 69.74),
      ("Mary", "2017-07-01", 59.44),
      ("Mary", "2017-07-05", 80.14))
      .toDF("name", "tx_date", "amount")

    val forRankingWindow = Window.partitionBy("name").orderBy($"amount".desc)
    val txDataWithRankDF = txDataDF.withColumn("rank", functions.rank().over(forRankingWindow))
    txDataWithRankDF.where('rank <= 3).show(10)

    val forEntireRangeWindow = Window.partitionBy("name")
      .orderBy($"amount".desc)
      .rangeBetween(Window.unboundedPreceding,
        Window.unboundedFollowing)
    val amountDifference = functions.max(txDataDF("amount")).over(forEntireRangeWindow) - txDataDF("amount")
    val txDiffWithHighestDF = txDataDF.withColumn("amount_diff", functions.round(amountDifference, 3))
    txDiffWithHighestDF.show

    val forMovingAvgWindow = Window.partitionBy("name").orderBy("tx_date")
      .rowsBetween(Window.currentRow - 1, Window.currentRow + 1)
    val txMovingAvgDF = txDataDF.withColumn("moving_avg",
      functions.round(functions.avg("amount").over(forMovingAvgWindow), 2))
    txMovingAvgDF.show
  }

  override def getJobName(): String = "WindowFuncs"
}
