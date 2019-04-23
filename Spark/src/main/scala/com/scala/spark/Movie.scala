package com.scala.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class Movie extends SparkJob {

  def displayHighestRatedMoviePerYear(movieFile: RDD[String], movieRatings: RDD[String]) = {
    val actorsGroupedByMovies = movieFile
      .map(line => {
        val split = line.split("\t")
        ((split(1), split(2)), split(0))
      }).reduceByKey(_ + "," + _)


    val movieRatingsMapped = movieRatings.map(line => {
      val split = line.split("\t")
      ((split(1), split(2)), (split(0)))
    })

    actorsGroupedByMovies.fullOuterJoin(movieRatingsMapped)
      .map({ case (movieYear: (String, String), actorsRating: (Option[String], Option[String])) =>
        (movieYear._2, (movieYear._1, actorsRating._2.getOrElse("0"), actorsRating._1))
      }).reduceByKey((acc, num) => {
      if (acc._2.toFloat > num._2.toFloat)
        acc
      else
        num
    }).sortByKey().foreach(println)

    actorsGroupedByMovies.join(movieRatingsMapped)
      .map({ case (movieYear: (String, String), actorsRating: (String, String)) =>
        (movieYear._2, (movieYear._1, actorsRating._2, actorsRating._1))
      }).reduceByKey((acc, num) => {
      if (acc._2.toFloat > num._2.toFloat)
        acc
      else
        num
    }).sortByKey().foreach(println)

  }

  override def execute(spark: SparkSession): Unit = {
    val movieFile = spark.sparkContext.textFile("movies.tsv")
    val movieRatings = spark.sparkContext.textFile("movie-ratings.tsv")

    displayHighestRatedMoviePerYear(movieFile, movieRatings)
    displayYearCount(movieFile)
    displayActorCount(movieFile)
    displayMostWorkedActors(movieFile)
  }

  def displayMostWorkedActors(movieFile: RDD[String]) = {
    val movies = movieFile
      .map(line => {
        val split = line.split("\t")
        (split(1), (split(0)))
      })

    val actorJoin = movies.join(movies)
    actorJoin.filter(a => !a._2._1.equals(a._2._2))
      .map(a => (a._2, 1))
      .reduceByKey(_ + _)
      .filter(a => a._2 > 1)
      .sortBy(_._2, ascending = false)
      .take(10)
      .foreach(println)
  }

  private def displayYearCount(movieFile: RDD[String]) = {
    movieFile
      .map(line => {
        val split = line.split("\t")
        (split(2), split(1))
      })
      .distinct()
      .map(a => (a._1, 1))
      .reduceByKey(_ + _)
      .sortBy(a => a._2, ascending = false)
      .foreach(println)
  }

  private def displayActorCount(movieFile: RDD[String]) = {
    movieFile
      .map(line => {
        val split = line.split("\t")
        (split(0), 1)
      })
      .reduceByKey(_ + _)
      .sortBy(a => a._2, ascending = false)
      .foreach(println)
  }

  override def getJobName(): String = "Movie"
}
