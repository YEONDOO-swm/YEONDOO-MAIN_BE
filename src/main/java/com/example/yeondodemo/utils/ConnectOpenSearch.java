package com.example.yeondodemo.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.client.*;


import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.CreateRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.indices.*;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.opensearch.client.util.ObjectBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ConnectOpenSearch {
        private String id;
        private String pw;
        private String url;
        public ConnectOpenSearch(String id, String pw, String url){
            this.id = id;
            this.pw = pw;
            this.url = url;
        };
        public void test2() throws IOException {
            RestClient restClient = null;
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(id, pw));
            //Initialize the client with SSL and TLS enabled
            restClient = RestClient.builder(new HttpHost(url, 445, "https")).
                    setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    }).build();
            OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            OpenSearchClient client = new OpenSearchClient(transport);    //Search for the document

            String index = "allcontent3";
            CreateRequest createIndexRequest = new CreateRequest.Builder().index(index).id("1d7e6096-ab80-4dce-a358-e57dfdb257c2").document("hi").build();
            client.indices().create((Function<CreateIndexRequest.Builder, ObjectBuilder<CreateIndexRequest>>) createIndexRequest);
            SearchResponse<IndexData> searchResponse = client.search(s -> s.index(index), IndexData.class);
            for (int i = 0; i< searchResponse.hits().hits().size(); i++) {
                System.out.println(searchResponse.hits().hits().get(i).source());
            }
        }

        public void test1() throws IOException {
            RestClient restClient = null;
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(id, pw));

            RestClientBuilder builder = RestClient.builder(new HttpHost(url, 443, "https"))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    });
            RestHighLevelClient client = new RestHighLevelClient(builder);
            //Index some data

            String index = "allcontent3";

            GetRequest getRequest = new GetRequest(index);
            List<String> strings = new ArrayList<>();
            RequestOptions aDefault = RequestOptions.DEFAULT;
            GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println("response = " + response);

//
//            IndexData indexData = new IndexData();
//            IndexRequest<IndexData> indexRequest = new IndexRequest.Builder<IndexData>().index(index).id("1d7e6096-ab80-4dce-a358-e57dfdb257c2").document(indexData).build();
//            client.index(indexRequest);
            //Search for the document
//            SearchResponse<IndexData> searchResponse = client.search(s -> s.index(index), IndexData.class);
//            for (int i = 0; i< searchResponse.hits().hits().size(); i++) {
//                System.out.println(searchResponse.hits().hits().get(i).source());

    }
    @Getter @Setter @ToString
    static class IndexData {
        private List<String> Authors;
        private String Title;
        private String paper_id;
        private String Published;
        private List<String> links;
    }
}
