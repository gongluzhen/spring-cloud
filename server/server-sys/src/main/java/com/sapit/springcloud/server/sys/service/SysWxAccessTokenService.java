package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.WeixinUtil;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysWxAccessToken;
import com.sapit.springcloud.server.sys.dao.SysWxAccessTokenDao;
@Service
@Transactional(readOnly = true)
public class SysWxAccessTokenService extends CrudService<SysWxAccessTokenDao, SysWxAccessToken> {
	@Autowired
	private WeixinUtil weixinUtil;
	
	public SysWxAccessToken get(String id) {
		return super.get(id);
	}

	public List<SysWxAccessToken> findList(SysWxAccessToken sysWxAccessToken) {
		return super.findList(sysWxAccessToken);
	}

	public Page<SysWxAccessToken> findPage(SysWxAccessToken sysWxAccessToken) {
		return super.findPage(sysWxAccessToken);
	}

	@Transactional(readOnly = false)
	public SysWxAccessToken save(SysWxAccessToken sysWxAccessToken) {
		return super.save(sysWxAccessToken);
	}

	@Transactional(readOnly = false)
	public SysWxAccessToken getAccessTokenAndJSSDKTicket() {
		SysWxAccessToken wxAccessToken = this.dao.getByAppId(weixinUtil.getAppId());
		if (wxAccessToken != null) {
			if ((System.currentTimeMillis() - wxAccessToken.getCreateTime()) / 1000 > (wxAccessToken.getExpiresIn() - 120)) {
				JSONObject jsonObject = weixinUtil.getAccessToken(weixinUtil.getAppId(), weixinUtil.getAppSecret());
				wxAccessToken.setAccessToken(jsonObject.getString("access_token"));
				wxAccessToken.setCreateTime(System.currentTimeMillis());
				wxAccessToken.setExpiresIn(jsonObject.getLong("expires_in"));

				jsonObject = weixinUtil.getJSSDKTicket(wxAccessToken.getAccessToken());
				wxAccessToken.setJsapiTicket(jsonObject.getString("ticket"));

				this.dao.update(wxAccessToken);
			}
		} else {
			wxAccessToken = new SysWxAccessToken();
			wxAccessToken.setAppId(weixinUtil.getAppId());

			JSONObject jsonObject = weixinUtil.getAccessToken(weixinUtil.getAppId(), weixinUtil.getAppSecret());
			wxAccessToken.setAccessToken(jsonObject.getString("access_token"));
			wxAccessToken.setCreateTime(System.currentTimeMillis());
			wxAccessToken.setExpiresIn(jsonObject.getLong("expires_in"));

			jsonObject = weixinUtil.getJSSDKTicket(wxAccessToken.getAccessToken());
			wxAccessToken.setJsapiTicket(jsonObject.getString("ticket"));

			this.dao.insert(wxAccessToken);
		}

		return wxAccessToken;
	}

	@Transactional(readOnly = false)
	public void delete(SysWxAccessToken sysWxAccessToken) {
		super.delete(sysWxAccessToken);
	}

}