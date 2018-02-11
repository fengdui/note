package com.fengdui.test.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author FD
  * @date 2017/12/24
  * @version v6.2.0
  */
class Rdd {

}
object Rdd {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("fd").setMaster("local")
        val sc = new SparkContext(conf);
        val rdd = sc.parallelize(List("sss", "ddd"));
        val filt = rdd.filter( s => s.contains("fd"));
        rdd.map()
        rdd.aggregate()
        print(filt)
    }
}
