package com.sapit.springcloud.common.server.mybatis;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.sapit.springcloud.common.server.druid.DruidConfiguration;
import com.sapit.springcloud.moudle.BaseEntity;

@Configuration
@AutoConfigureAfter(DruidConfiguration.class)
public class MyBatisConfig {
	@Bean("sqlSessionFactoryBean")
	@ConditionalOnMissingBean // 当容器里没有指定的Bean的情况下创建该对象
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.sapit.springcloud.moudle");
		sqlSessionFactoryBean.setTypeAliasesSuperType(BaseEntity.class);
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mappings/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:/mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}
}
