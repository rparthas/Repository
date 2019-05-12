package com.scala.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

import scala.util.Random

class SparkQLFuncs extends SparkJob {
  override def execute(spark: SparkSession) = {
    val sqlContext = spark.sqlContext
    import sqlContext.implicits._

    val rdd = spark.sparkContext.parallelize(1 to 10).map(x => (x, Random.nextInt(100) * x))
    val kvDF = rdd.toDF("key", "value")
    kvDF.show()
    kvDF.printSchema()

    val movieSchema = StructType(Array(StructField("actor_name", StringType, true),
      StructField("movie_title", StringType, true),
      StructField("produced_year", LongType, true)))

    var movies = spark.read.option("header", "false").option("sep", "\t")
      .schema(movieSchema).csv("data/movie/movies.tsv")
    movies.show(10)
    movies = spark.read.load("data/movie/movies.parquet")
    movies.show(10)

    movies.select("actor_name").show(10)
    movies.select('produced_year - ('produced_year % 10) as 'decade).show(10)
    movies.filter("actor_name == 'McClure, Marc (I)'")
      .select('actor_name, 'movie_title).show(10)
    movies.selectExpr("*", "(produced_year - (produced_year % 10)) as decade").show(5)
    movies.where('produced_year > 2000).show(10)

    movies.select("movie_title").distinct.selectExpr("count(movie_title) as movies").show
    val movieTitles = movies.dropDuplicates("movie_title")
      .selectExpr("movie_title", "length(movie_title) as title_length", "produced_year")
    movieTitles.sort('title_length).show(5)
    movieTitles.orderBy('title_length.desc, 'produced_year).show(5)
    movieTitles.orderBy('title_length.desc, 'produced_year).limit(10).show

    val smallerMovieDFs = movies.randomSplit(Array(0.6, 0.3, 0.1))
    println(smallerMovieDFs(0).count + smallerMovieDFs(1).count + smallerMovieDFs(2).count, "===", movies.count)

    val moviesDS = movies.as[com.scala.spark.model.Movie]
    moviesDS.createOrReplaceTempView("movies")
    spark.catalog.listTables.show

    spark.sql("select * from movies where actor_name like '%Jolie%' and produced_year > 2009").show


  }

  override def getJobName(): String = "SparkQlFuncs"
}
