package com.example.demo;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaSpringTestApplicationTests {

	@Test
	public void contextLoads() {
		String clusterName = "elasticsearch";
		int port = 9300;
		String host = "127.0.0.1";
		// on startup
		TransportClient client = null;
		Settings settings = Settings.builder()
				.put("client.transport.sniff", true)
				.put("cluster.name", clusterName).build();
		try{
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));


			SearchResponse response = client.prepareSearch("hello")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					// .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
					.setPostFilter(QueryBuilders.rangeQuery("types").from(0).to(18))     // Filter
					.get();


		} catch (Exception e) {
			System.out.println(e);
// on shutdown

			client.close();
		}
	}

}
