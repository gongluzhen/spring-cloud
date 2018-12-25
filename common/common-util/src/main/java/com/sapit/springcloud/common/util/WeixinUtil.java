package com.sapit.springcloud.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 公众平台通用接口工具类
 * 
 * @author Administrator
 *
 */
@Component
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	@Value("${weixin.appId}")
	private String appId;
	@Value("${weixin.appSecret}")
	private String appSecret;
	@Value("${weixin.access_token_url}")
	private String access_token_url;
	@Value("${weixin.menu_create_url}")
	private String menu_create_url;
	@Value("${weixin.jssdk_ticket_url}")
	private String jssdk_ticket_url;
	@Value("${weixin.user_list_url}")
	private String user_list_url;
	@Value("${weixin.user_info_url}")
	private String user_info_url;
	@Value("${weixin.user_openid_url}")
	private String user_openid_url;

	public String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	public String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		// 这里的jsapi_ticket是获取的jsapi_ticket。
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&nonceStr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		// System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("appId", this.appId);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 获取accesstoken
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public JSONObject getAccessToken(String appid, String appsecret) {
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	public JSONObject getUserOpenId(String code) {
		String requestUrl = user_openid_url.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	/**
	 * 获取微信JSSDK的ticket
	 * 
	 * @author Benson
	 */
	public JSONObject getJSSDKTicket(String access_token) {
		String requestUrl = jssdk_ticket_url.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("https request error:{}", e);
			System.out.println("发起https请求失败！");
		}
		return jsonObject;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAccess_token_url() {
		return access_token_url;
	}

	public void setAccess_token_url(String access_token_url) {
		this.access_token_url = access_token_url;
	}

	public String getMenu_create_url() {
		return menu_create_url;
	}

	public void setMenu_create_url(String menu_create_url) {
		this.menu_create_url = menu_create_url;
	}

	public String getJssdk_ticket_url() {
		return jssdk_ticket_url;
	}

	public void setJssdk_ticket_url(String jssdk_ticket_url) {
		this.jssdk_ticket_url = jssdk_ticket_url;
	}

	public String getUser_list_url() {
		return user_list_url;
	}

	public void setUser_list_url(String user_list_url) {
		this.user_list_url = user_list_url;
	}

	public String getUser_info_url() {
		return user_info_url;
	}

	public void setUser_info_url(String user_info_url) {
		this.user_info_url = user_info_url;
	}

	public String getUser_openid_url() {
		return user_openid_url;
	}

	public void setUser_openid_url(String user_openid_url) {
		this.user_openid_url = user_openid_url;
	}

}
