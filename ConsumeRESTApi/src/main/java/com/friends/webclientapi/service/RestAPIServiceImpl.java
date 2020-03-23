package com.friends.webclientapi.service;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.friends.webclientapi.beans.Quote;

import reactor.core.publisher.Mono;

@Service
public class RestAPIServiceImpl  implements RestAPIService{
	
	private WebClient webClient;
	
	@Value("${client.URL}")
	private String url;
	
	private RestTemplate restTemplate;
	
	
	
	 @PostConstruct
	 private void init() {
		 webClient = WebClient.builder()
					.baseUrl(url)
					.defaultHeader(HttpHeaders.CONTENT_TYPE,  "application/json")
					.defaultHeader(HttpHeaders.ACCEPT,"application/json")
					.defaultHeader(HttpHeaders.ACCEPT_CHARSET, "ACCEPT_CHARSET", "UTF-8")
					.build();
		 
		 restTemplate = new RestTemplate(getClientHttpRequestFactory());
		 
	 }
	 
	//Override timeouts in request factory
	 private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
	 {
	     HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
	                       = new HttpComponentsClientHttpRequestFactory();
	     //Connect timeout
	     clientHttpRequestFactory.setConnectTimeout(10_000);
	      
	     //Read timeout
	     clientHttpRequestFactory.setReadTimeout(10_000);
	     return clientHttpRequestFactory;
	 }

	@Override
	public Mono<Quote> getQuote() {
		Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		
		System.out.println("Quote : " +quote.toString());
		
		return webClient.get().uri("/api/random")
						.exchange()
						.timeout(Duration.ofMillis(2000))
						.flatMap(clientResponse -> clientResponse.bodyToMono(Quote.class));
	}

}
