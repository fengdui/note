package com.fengdui.test.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author FD
  * @date 2017/10/24
  */
class SparkApplication {
    def createSparkContext() = {
        List()
    }
}
object SparkApplication {
    def main(args:Array[String]) {
        println("Hello, Scala");
        val conf = new SparkConf().setAppName("fd").setMaster("local")
        val sc = new SparkContext(conf);
        val rdd: RDD[String] = sc.textFile("");
        var dataRDD = sc.parallelize(List(1, 2, 3))
        var sqlContext = new SQLContext(conf)

  }
}
