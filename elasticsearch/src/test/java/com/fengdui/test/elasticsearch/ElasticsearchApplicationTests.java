package com.fengdui.test.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

	private TransportClient client;
	@Before
	public void getClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", "fd-cluster").build();
		client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

	}
	@Test
	public void contextLoads() throws ExecutionException, InterruptedException {
		GetResponse response = client.prepareGet("", "","")
				.setStoredFields().execute().actionGet();
		IndexResponse indexResponse = client.prepareIndex("", "", "").execute().actionGet();

		SearchResponse searchResponse = client.prepareSearch()
	}

}
