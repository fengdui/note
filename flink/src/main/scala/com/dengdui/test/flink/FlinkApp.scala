//package com.dengdui.test.flink
///**
//  * @author FD
//  * @date 2018/1/16
//  * @version v6.2.0
//  */
//
//import org.apache.flink.api.common.typeinfo.TypeInformation
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.api.windowing.time.Time
//
//object FlinkApp {
//    def main(args: Array[String]) {
//
//        val env = StreamExecutionEnvironment.getExecutionEnvironment
//        val text = env.socketTextStream("localhost", 9999)
//        implicit val typeInfo = TypeInformation.of(classOf[(String,Int)])
//        val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
//            .map { (_, 1) }
//            .keyBy(0)
//            .timeWindow(Time.seconds(5))
//            .sum(1)
//
//        counts.print()
//
//        env.execute("Window Stream WordCount")
//    }
//}
