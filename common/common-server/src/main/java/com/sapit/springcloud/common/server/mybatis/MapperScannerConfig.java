package com.sapit.springcloud.common.server.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;

@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		mapperScannerConfigurer.setBasePackage("com.sapit.springcloud");
		mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class);
		return mapperScannerConfigurer;
	}
}
