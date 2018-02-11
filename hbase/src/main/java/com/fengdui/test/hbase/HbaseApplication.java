package com.fengdui.test.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HbaseApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(HbaseApplication.class, args);
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.master", "192.168.243.129:16000");
		conf.set("hbase.zookeeper.quorum", "192.168.243.129");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf("user"));
		Put put = new Put(Bytes.toBytes("name"));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("fd"), Bytes.toBytes("111"));
		table.put(put);
//		Bytes.putByte()
		System.out.println("x");
		HBaseAdmin hBaseAdmin = (HBaseAdmin) connection.getAdmin();
		hBaseAdmin.createTable();
		new Watcher() {
			@Override
			public void process(WatchedEvent event) {

			}
		};
	}
}
