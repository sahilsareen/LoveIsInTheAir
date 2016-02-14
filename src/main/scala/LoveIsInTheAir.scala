/**
  * Copyright (C) 2016 Sahil Sareen (sahil [DOT] sareen [AT] hotmail [DOT] com)
  *
  * This program is free software: you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation, either version 3 of the License, or (at your option) any later
  * version. See http://www.gnu.org/copyleft/gpl.html the full text of the
  * license.
  */

package LoveIsInTheAir

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, HashPartitioner}

object LoveIsInTheAir {
  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("""Usage: TwitterPopularTags <consumer key> <consumer secret>
        | <access token> <access token secret> [<filters>]""".stripMargin.replaceAll("\n", ""))
      System.exit(1)
    }

    val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    val filters = args.takeRight(args.length - 4)

    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    val sparkConf = new SparkConf()
      .setAppName("LoveIsInTheAir")
      .setMaster("local[2]")

    val ssc = new StreamingContext(sparkConf, Seconds(10))
    val stream = TwitterUtils.createStream(ssc, None, filters)
    ssc.checkpoint("./Checkpoint")

    val initialRDD: RDD[(String, Int)] = ssc.sparkContext.emptyRDD
    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
      val currentCount = values.sum
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }

    val citiesUpdateFunc = (iterator: Iterator[(String, Seq[Int], Option[Int])]) =>
      iterator.flatMap(t => updateFunc(t._2, t._3).map(s => (t._1, s)))

    val indianCitiesInLove = stream
      .filter(status => status.getPlace() != null && status.getPlace().getCountryCode == "IN")
      .map(_.getPlace.getName)

    val cityPairs = indianCitiesInLove.map(city => (city, 1))
    val cityCounts = cityPairs.reduceByKey(_ + _)

    cityCounts.print()

    val stateDstream = cityCounts.updateStateByKey[Int](
      citiesUpdateFunc,
      new HashPartitioner (ssc.sparkContext.defaultParallelism), true, initialRDD)

    stateDstream.print()
    stateDstream.saveAsTextFiles("STATE")

    ssc.start()
    ssc.awaitTermination()
  }
}
