package com.friends.webclientapi.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friends.webclientapi.beans.Quote;
import com.friends.webclientapi.service.RestAPIService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${endPoint}")
public class RestAPIController {
	
	@Autowired
	private RestAPIService restAPIService;
	
	@Value("${txn.timeout}")
	private long txnTimeout;
	
	@GetMapping("/getQuote")
	public Mono<Quote> getQuote() {
		return restAPIService.getQuote()
				.timeout(Duration.ofMillis(txnTimeout));
	}

}
