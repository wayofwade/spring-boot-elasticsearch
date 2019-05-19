package com.example.demo;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class JavaSpringTestApplication {

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("hello world hhaha");
		connect();
		SpringApplication.run(JavaSpringTestApplication.class, args);

	}

	public static void connect() throws UnknownHostException {
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
					.setFrom(0).setSize(60).setExplain(true)
					.get();
			System.out.println("hello------------");
			System.out.println(response);
			System.out.println(response.toString());
			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e);
// on shutdown

		client.close();
		}
	}

}
