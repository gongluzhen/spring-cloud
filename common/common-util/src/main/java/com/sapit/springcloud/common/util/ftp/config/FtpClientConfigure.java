package com.sapit.springcloud.common.util.ftp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sapit.springcloud.common.util.ftp.core.FtpClientFactory;
import com.sapit.springcloud.common.util.ftp.core.FtpClientTemplate;

@Configuration
@EnableConfigurationProperties(FtpClientProperties.class)
public class FtpClientConfigure {

	private FtpClientProperties ftpClientProperties;

	@Autowired
	public void setFtpClientProperties(FtpClientProperties ftpClientProperties) {
		this.ftpClientProperties = ftpClientProperties;
	}

	@Bean
	public FtpClientFactory getFtpClientFactory() {
		return new FtpClientFactory(ftpClientProperties);
	}

	@Bean
	public FtpClientTemplate getFtpTemplate() {
		return new FtpClientTemplate(getFtpClientFactory());
	}

}
