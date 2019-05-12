package com.scala.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession, functions}

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
    val movieFile = spark.sparkContext.textFile("data/movie/movies.tsv")
    val movieRatings = spark.sparkContext.textFile("data/movie/movie-ratings.tsv")

        displayHighestRatedMoviePerYear(movieFile, movieRatings)
        displayYearCount(movieFile)
        displayActorCount(movieFile)
        displayMostWorkedActors(movieFile)


    val movies = spark.sqlContext.read.load("data/movie/movies.parquet")
    import spark.sqlContext.implicits._
        displayCountByYear(movies, spark.sqlContext)
        displayCountByActorName(movies, spark.sqlContext)
    val movieRatingsDF = movieRatings.map(line => {
      val split = line.split("\t")
      (split(0), split(1), split(2))
    }).toDF("movie_rating", "movie_title", "produced_year")
    displayHighestRatedMoviePerYear(movies, movieRatingsDF, spark.sqlContext)
  }

  def displayHighestRatedMoviePerYear(movies: DataFrame, movieRatings: DataFrame, sqlContext: SQLContext) = {
    import sqlContext.implicits._


    val movieDetails = movies.groupBy("produced_year", "movie_title")
      .agg(functions.collect_set("actor_name").as("actors"))
      .join(functions.broadcast(movieRatings), Seq("movie_title", "produced_year"), "outer")
      .select(
        functions.coalesce($"produced_year", functions.lit("0000")).as("year"),
        functions.coalesce($"movie_title", functions.lit("no name")).as("title"),
        'movie_rating.as("rating"),
        'actors
      )


    val rankingWindow = Window.partitionBy('year).orderBy('rating.desc)
    movieDetails.withColumn("rank", functions.rank().over(rankingWindow))
      .where('rank <= 1)
      .sort($"year")
      .show(100)


    movieDetails
      .sort($"year", $"rating".desc)
      .groupBy($"year")
      .agg(functions.first($"title"),
        functions.first($"actors"),
        functions.first($"rating"))
      .sort($"year")
      .show(100)

  }

  private def displayCountByActorName(movies: DataFrame, sqlContext: SQLContext) = {
    import sqlContext.implicits._
    movies.select("actor_name", "produced_year")
      .groupBy('actor_name)
      .count()
      .sort($"count".desc)
      .show(100)
  }

  private def displayCountByYear(movies: DataFrame, sqlContext: SQLContext) = {
    import sqlContext.implicits._
    movies.select("movie_title", "produced_year")
      .groupBy('produced_year)
      .count()
      .sort($"count".desc)
      .show(50)
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
