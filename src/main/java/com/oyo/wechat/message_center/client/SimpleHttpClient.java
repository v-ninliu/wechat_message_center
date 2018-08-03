package com.oyo.wechat.message_center.client;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SimpleHttpClient {

  @Autowired
  private RestTemplate restTemplate;

  public String simplePost(String requestJson, String url) throws Exception {

    HttpHeaders headers = getJsonHeaders();
    ResponseEntity<String> responseEntity = post(requestJson, url, headers);
    return responseEntity.getBody().toString();
  }

  public String simpleGet(String url) throws Exception {

    restTemplate.getMessageConverters().set(1,
        new StringHttpMessageConverter(StandardCharsets.UTF_8));
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    return responseEntity.getBody().toString();
  }


  public ResponseEntity<String> post(String requestJson, String url, HttpHeaders headers)
      throws Exception {

    HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
    return restTemplate.postForEntity(url, entity, String.class);
  }
  
  public ResponseEntity<String> patch(String requestJson, String url, HttpHeaders headers)
      throws Exception {
    headers.set("X-HTTP-Method-Override", "PATCH");
    return exchange(requestJson, url, headers, HttpMethod.PATCH.name());
  }

  public ResponseEntity<String> exchange(String requestJson,
                                         String url,
                                         HttpHeaders headers,
                                         String method)
      throws Exception {

    HttpMethod httpMethod = HttpMethod.POST;
    if (method.equalsIgnoreCase("get")) {
      httpMethod = HttpMethod.GET;
    } else if (method.equalsIgnoreCase("patch")) {
      httpMethod = HttpMethod.PATCH;
    }
    HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
    return restTemplate.exchange(url, httpMethod, entity, String.class);
  }

  public ResponseEntity<String> getWithToken(String url, String access_token) {
    restTemplate.getMessageConverters().set(1,
        new StringHttpMessageConverter(StandardCharsets.UTF_8));
    HttpHeaders headers = getJsonHeaders();
    headers.set("access_token", access_token);
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
  }
  
  public HttpHeaders getJsonHeaders() {

    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    headers.setContentType(type);
    return headers;
  }
}
