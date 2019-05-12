package com.scala.spark.model

import org.apache.spark.sql.streaming.GroupState
import org.apache.spark.sql.types.{DoubleType, StringType, StructType, TimestampType}

case class RackInfo(rack: String, temperature: Double, ts: java.sql.Timestamp)

case class RackState(var rackId: String, var highTempCount: Int,
                     var status: String, var lastTS: java.sql.Timestamp)

object Rack {
  def updateRackState(rackState: RackState, rackInfo: RackInfo): RackState = {
    val lastTS = Option(rackState.lastTS).getOrElse(rackInfo.ts)
    val withinTimeThreshold = (rackInfo.ts.getTime - lastTS.getTime) <= 60000
    val meetCondition = if (rackState.highTempCount < 1) true else withinTimeThreshold
    val greaterThanEqualTo100 = rackInfo.temperature >= 100.0
    (greaterThanEqualTo100, meetCondition) match {
      case (true, true) => {
        rackState.highTempCount = rackState.highTempCount + 1
        rackState.status = if (rackState.highTempCount >= 3) "Warning" else "Normal"
      }
      case _ => {
        rackState.highTempCount = 0
        rackState.status = "Normal"
      }
    }
    rackState.lastTS = rackInfo.ts
    rackState
  }

  def updateAcrossAllRackStatus(rackId: String, inputs: Iterator[RackInfo],
                                oldState: GroupState[RackState]): RackState = {
    var rackState = if (oldState.exists) oldState.get else RackState(rackId, 5, "", null)
    inputs.toList.sortBy(_.ts.getTime).foreach(input => {
      rackState = updateRackState(rackState, input)
      oldState.update(rackState)
    })
    rackState
  }

  val iotDataSchema = new StructType().add("rack", StringType, false)
    .add("temperature", DoubleType, false)
    .add("ts", TimestampType, false)
}
