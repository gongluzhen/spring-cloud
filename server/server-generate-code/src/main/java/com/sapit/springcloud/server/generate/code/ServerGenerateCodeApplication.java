package com.sapit.springcloud.server.generate.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = { "com.sapit.springcloud.client" })
@ComponentScan(basePackages = { "com.sapit.springcloud" })
@SpringBootApplication
public class ServerGenerateCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerGenerateCodeApplication.class, args);
	}
}
