package com.sapit.springcloud.common.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * MyX509TrustManager信任管理器
 * 
 * @author Administrator
 *
 */
public class MyX509TrustManager implements X509TrustManager {

	// 检查客户端证书
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	// 检查服务器端证书
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	// 返回受信任的x509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}