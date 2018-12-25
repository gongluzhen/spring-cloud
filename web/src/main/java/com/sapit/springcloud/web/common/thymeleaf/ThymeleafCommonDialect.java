package com.sapit.springcloud.web.common.thymeleaf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CommonDialect.class)
public class ThymeleafCommonDialect {
	@Bean
	@ConditionalOnMissingBean
	public CommonDialect commonDialect() {
		return new CommonDialect();
	}
}
