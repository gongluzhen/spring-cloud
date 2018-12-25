package com.sapit.springcloud.web;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = { "com.sapit.springcloud.client" })
@SpringBootApplication(scanBasePackages = { "com.sapit.springcloud" })
@ServletComponentScan(basePackages = { "com.sapit.springcloud.web" })
public class WebApplication extends SpringBootServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setHttpOnly(true);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
