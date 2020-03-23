package com.friends.webclientapi.service;

import com.friends.webclientapi.beans.Quote;

import reactor.core.publisher.Mono;

public interface RestAPIService {
	
	public Mono<Quote> getQuote();

}
