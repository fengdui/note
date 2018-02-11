package com.fengdui.test.spark.graphx

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.Graph
import org.apache.spark.graphx.util.GraphGenerators

/**
  * @author FD
  * @date 2017/12/22
  * @version v6.2.0
  */
class Disjkstra {

    val conf = new SparkConf().setAppName("fd").setMaster("local");
    val sc = new SparkContext(conf);
    val graph : Graph[Long, Int] = GraphGenerators.logNormalGraph(sc, 100).mapEdges(e => e.attr);
}
