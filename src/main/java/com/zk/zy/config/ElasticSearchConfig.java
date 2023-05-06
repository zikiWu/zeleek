package com.zk.zy.config;

import cn.jiguang.common.utils.StringUtils;
import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Data
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private Integer port;
    @Value("${elasticsearch.userName}")
    private String userName;
    @Value("${elasticsearch.password}")
    private String password;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        }

         RestClientBuilder builder = RestClient.builder(new HttpHost(host, port)).setRequestConfigCallback(requestConfigBuilder -> {
             requestConfigBuilder.setConnectTimeout(-1);
             requestConfigBuilder.setSocketTimeout(-1);
             requestConfigBuilder.setConnectionRequestTimeout(-1);
             return requestConfigBuilder;
         }).setHttpClientConfigCallback(httpClientBuilder -> {
             httpClientBuilder.disableAuthCaching();
             return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
         });
         return new RestHighLevelClient(builder);
    }
}