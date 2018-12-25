package com.sapit.springcloud.web.common.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sapit.springcloud.web.common.interceptor.LogInterceptor;
import com.sapit.springcloud.web.common.interceptor.MobileInterceptor;

@Configuration
public class WebMvcConfigurerAdapter implements WebMvcConfigurer {
	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}
	
	@Bean
	public MobileInterceptor mobileInterceptor() {
		return new MobileInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/login", "/sys/menu/tree", "/sys/menu/treeData");
		registry.addInterceptor(mobileInterceptor()).addPathPatterns("/**");
	}
	
	
}
