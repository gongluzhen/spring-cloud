package com.sapit.springcloud.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;  
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpClientProvider {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientProvider.class);
	private String methodType = "POST";
	private String encoding = "UTF-8";
	private Map<String, String> headParams = new HashMap<String, String>();
	private Map<String, String> params = new HashMap<String, String>();
	private String requestBody;
	private String url;
	private int responseCode = -1;
	private String responseContent;
	/** 链接超时 */
	private int	connectionTimeout = 1000 * 60 * 10;
	/** 读取超时 */
	private int soTimeout = 1000 * 60 * 10;
	
	public static HttpClientProvider getInstance(){
		return new HttpClientProvider();
	}
	
	public static String connect(String url, String content) throws Exception {
		byte[] outData = content.getBytes("UTF-8");
		if (outData == null) {
			throw new NullPointerException();
		}
		URL rui = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		byte[] data = null;

		try {
			rui = new URL(url);
			// 设置连接属性
			conn = (HttpURLConnection) rui.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");

			// 设置超时时间
			conn.setConnectTimeout(60 * 6000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
			conn.setRequestProperty("Content-Length", String.valueOf(outData.length));
			conn.setRequestProperty("Charset", "UTF-8");
			
			OutputStream os = conn.getOutputStream();
			if (os != null) {
				os.write(outData);
				os.flush();
				os.close();
			}

			int code = conn.getResponseCode();
			// 判断HTTPCODE是否正常
			if (code == HttpURLConnection.HTTP_OK) {
				inputStream = conn.getInputStream();
				byteArrayOutputStream = new ByteArrayOutputStream();
				// 一个个字符读取，2g手机网络一个TCP包较小
				byte[] buf = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) != -1) {
					byteArrayOutputStream.write(buf, 0, len);
				}
				data = byteArrayOutputStream.toByteArray();
			} else {
			}
		} catch (Exception e) {
			// 此处不处理，在doInBackground循环中处理IO异常
			throw e;
		} finally {
			// close 流和连接
			if (conn != null) {
				conn.disconnect();
			}
			try {
				if (inputStream != null)
					inputStream.close();
				if (byteArrayOutputStream != null)
					byteArrayOutputStream.close();
			} catch (Exception e) {
			}
		}
		return new String(data, "UTF-8");
	}
	
	public String send() {
		logger.info("request method : " + methodType);
		if ("POST".equalsIgnoreCase(this.methodType)) {
			responseContent = post();
		} else if ("GET".equalsIgnoreCase(this.methodType)) {
			responseContent = get();
		}
		return responseContent;
	}
	
	
	private String post() {
		logger.info("used request url : " + url);	
		PostMethod method = new PostMethod(this.url);
		method.addRequestHeader("Connection", "close");
		addHeadParams(method);

		if (null != params && params.size() > 1) {
			Iterator<String> paramsIT = params.keySet().iterator();
			while (paramsIT.hasNext()) {
				String key = paramsIT.next();
				String value = params.get(key);
				logger.info("request param [ key : " + key + " ;value : "
						+ value + " ;]");
				method.addParameter(key, value);
			}
		}

		if (null != requestBody && !"".equals(requestBody.trim())) {
			logger.info("request body : " + requestBody);
			method.setRequestBody(requestBody);
			method.setRequestContentLength(requestBody.length());
		}
        method.getParams().setContentCharset(this.encoding);

		try {
			return executeMethod(method);
		} catch (Exception e) {
			logger.error("POST request error : ",e);
			return null;
		}
	}

	private String get() {
		logger.info("used request url : " + url);
		GetMethod method = new GetMethod(this.url);
		method.addRequestHeader("Connection", "close");
		addHeadParams(method);
		try {
			return executeMethod(method);
		} catch (Exception e) {
			logger.error("GET request error : ",e);
			return null;
		}
	}

	
	private String executeMethod(HttpMethod method) throws HttpException, IOException {
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.executeMethod(method);
			httpClient.setConnectionTimeout(this.connectionTimeout);
			httpClient.getParams().setSoTimeout(this.soTimeout);
			
			this.responseCode = method.getStatusCode();
			logger.info("response code : " + responseCode);
			logger.info("response encoding : "+encoding);
			String result =new String( IOUtils.toString(method.getResponseBodyAsStream(),this.encoding));
			logger.info("response content : "+result);
			return result;
		} catch (HttpException he) {
			throw he;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			method.releaseConnection();
		}
	}

	private void addHeadParams(HttpMethod method) {
		if (null != headParams && headParams.size() > 1) {
			Iterator<String> headParamIT = headParams.keySet().iterator();
			while (headParamIT.hasNext()) {
				String key = headParamIT.next();
				String value = headParams.get(key);
				logger.info("request head param [ key : " + key + " ;value : "
						+ value + " ;]");
				method.addRequestHeader(key, value);
			}
		}
	}


	public HttpClientProvider setPostMethod() {
		this.methodType = "POST";
		return this;
	}

	
	public HttpClientProvider setGetMethod() {
		this.methodType = "GET";
		return this;
	}

	public HttpClientProvider setHeadParams(Map<String, String> headParams) {
		this.headParams = headParams;
		return this;
	}

	
	public HttpClientProvider setParams(Map<String, String> params) {
		this.params = params;
		return this;
	}

	
	public HttpClientProvider addParam(String key, String value) {
		if (null == this.params)
			params = new HashMap<String, String>();
		params.put(key, value);
		return this;
	}

	
	public HttpClientProvider addHeadParam(String key, String value) {
		if (null == this.headParams)
			this.headParams = new HashMap<String, String>();
		this.headParams.put(key, value);
		return this;
	}

	
	public HttpClientProvider setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	
	public HttpClientProvider setRequestBody(String requestBody) {
		this.requestBody = requestBody;
		return this;
	}

	
	public HttpClientProvider setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getMethodType() {
		return methodType;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getUrl() {
		return url;
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

}
