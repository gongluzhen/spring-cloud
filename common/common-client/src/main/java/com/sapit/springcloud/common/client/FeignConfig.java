package com.sapit.springcloud.common.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import feign.Retryer;

@Configuration
public class FeignConfig {
	@Bean
	@Primary
	Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
}
