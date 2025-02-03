package com.rlj.dietAssist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
public class AppConfig {

  @Bean
  public RestTemplate restTemplate(){

    RestTemplate restTemplate = new RestTemplate();

    // 자동 인코딩 방지 설정
    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
    factory.setEncodingMode(EncodingMode.NONE);
    restTemplate.setUriTemplateHandler(factory);

    return new RestTemplate();
  }

}
