package com.oyo.wechat.message_center.config;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
public class AppConfiguration {


  // connection pooling
  @Value("${httpClient.connection.pool.size}")
  private String poolMaxTotal;

  @Value("${httpClientFactory.connection.timeout}")
  private String connectionTimeOut;

  @Value("${httpClientFactory.read.timeout}")
  private String readTimeOut;


  @Bean
  public ClientHttpRequestFactory httpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory(httpClient());
    factory.setConnectTimeout(Integer.parseInt(connectionTimeOut));
    factory.setReadTimeout(Integer.parseInt(readTimeOut));
    return factory;
  }

  @Bean
  public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
    RestTemplate template = new RestTemplate();

    template.setRequestFactory(httpRequestFactory);

//    ((SimpleClientHttpRequestFactory) template.getRequestFactory())
//        .setConnectTimeout(Integer.parseInt(connectionTimeOut));
//    ((SimpleClientHttpRequestFactory) template.getRequestFactory())
//        .setReadTimeout(Integer.parseInt(readTimeOut));
    List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
    messageConverters.add(new FormHttpMessageConverter());
    template.setMessageConverters(messageConverters);

    return template;
  }

  @Bean
  public HttpClient httpClient() {
    PoolingHttpClientConnectionManager connectionManager =
        new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(Integer.parseInt(poolMaxTotal));
    return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
  }

}




