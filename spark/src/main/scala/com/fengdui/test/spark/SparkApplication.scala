package com.fengdui.test.spark

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
        var dataRDD = sc.parallelize(List(1, 2, 3))


  }
}
