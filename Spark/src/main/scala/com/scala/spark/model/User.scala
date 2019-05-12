package com.scala.spark.model


import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.streaming.GroupState
import org.apache.spark.sql.types.{StringType, StructType, TimestampType}

case class UserActivity(user: String, action: String,
                        page: String, ts: java.sql.Timestamp)

case class UserSessionState(var user: String, var status: String, var startTS: java.sql.Timestamp,
                            var endTS: java.sql.Timestamp, var lastTS: java.sql.Timestamp,
                            var numPage: Int)

case class UserSessionInfo(userId: String, start: java.sql.Timestamp, end: java.sql.Timestamp, numPage: Int)


object User {
  def updateUserActivity(userSessionState: UserSessionState, userActivity: UserActivity): UserSessionState = {
    userActivity.action match {
      case "login" => {
        userSessionState.startTS = userActivity.ts
        userSessionState.status = "Online"
      }
      case "logout" => {
        userSessionState.endTS = userActivity.ts
        userSessionState.status = "Offline"
      }
      case _ => {
        userSessionState.numPage += 1
        userSessionState.status = "Active"
      }
    }
    userSessionState.lastTS = userActivity.ts
    userSessionState
  }

  def updateAcrossAllUserActivities(user: String, inputs: Iterator[UserActivity],
                                    oldState: GroupState[UserSessionState]): Iterator[UserSessionInfo] = {
    var userSessionState = if (oldState.exists) oldState.get else UserSessionState(user, "",
      new java.sql.Timestamp(System.currentTimeMillis), null, null, 0)
    var output = ListBuffer[UserSessionInfo]()
    inputs.toList.sortBy(_.ts.getTime).foreach(userActivity => {
      userSessionState = updateUserActivity(userSessionState, userActivity)
      oldState.update(userSessionState)
      if (userActivity.action == "login") {
        output += UserSessionInfo(user, userSessionState.startTS,
          userSessionState.endTS, 0)
      }
    })
    val sessionTimedOut = oldState.hasTimedOut
    val sessionEnded = !Option(userSessionState.endTS).isEmpty
    val shouldOutput = sessionTimedOut || sessionEnded
    shouldOutput match {
      case true => {
        if (sessionTimedOut) {
          import java.util.Calendar
          val cal = Calendar.getInstance
          cal.setTimeInMillis(oldState.get.lastTS.getTime)
          cal.add(Calendar.MINUTE, 30)
          userSessionState.endTS = new java.sql.Timestamp(cal.getTimeInMillis)
        }
        oldState.remove()
        output += UserSessionInfo(user, userSessionState.startTS,
          userSessionState.endTS, userSessionState.numPage)
      }
      case _ => {
        oldState.update(userSessionState)
        oldState.setTimeoutTimestamp(userSessionState.lastTS.getTime, "30 minutes")
      }
    }
    output.iterator
  }

  val userActivitySchema = new StructType().add("user", StringType, false)
    .add("action", StringType, false)
    .add("page", StringType, false)
    .add("ts", TimestampType, false)
}