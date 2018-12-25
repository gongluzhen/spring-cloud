package com.sapit.springcloud.server.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.DateUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.dao.SysUserFpwdDao;

@Service
@Transactional(readOnly = true)
public class SysUserFpwdService extends CrudService<SysUserFpwdDao, SysUserFpwd> {

	public SysUserFpwd get(String id) {
		return super.get(id);
	}

	public List<SysUserFpwd> findList(SysUserFpwd sysUserFpwd) {
		return super.findList(sysUserFpwd);
	}

	public Page<SysUserFpwd> findPage(Page<SysUserFpwd> page, SysUserFpwd sysUserFpwd) {
		return super.findPage(sysUserFpwd);
	}

	@Transactional(readOnly = false)
	public SysUserFpwd save(SysUserFpwd sysUserFpwd) {
		return super.save(sysUserFpwd);
	}

	@Transactional(readOnly = false)
	public void delete(SysUserFpwd sysUserFpwd) {
		super.delete(sysUserFpwd);
	}

	/**
	 * 检测记录存在及是否活跃
	 *
	 * @param tokenId
	 *            标示
	 * @return 记录
	 */
	public SysUserFpwd checkToken(String tokenId) {
		SysUserFpwd findSysUserFpwd = dao.getByParams(null, tokenId);
		if (findSysUserFpwd == null || findSysUserFpwd.getActiveTime() == null) {
			return null;
		}
		Date now = new Date();
		if (findSysUserFpwd.getActiveTime().getTime() < now.getTime()) {
			return null;
		}
		return findSysUserFpwd;
	}

	/**
	 * 保存忘记密码信息
	 *
	 * @param user
	 *            通过登录名称查询出的User
	 * @param tokenId
	 *            令牌
	 * @author YJH
	 */
	@Transactional(readOnly = false)
	public void save(User user, String tokenId) {
		dao.deleteByUserId(user.getId()); // 删除以前的记录

		SysUserFpwd sysUserFpwd = new SysUserFpwd();
		sysUserFpwd.setUser(user);
		sysUserFpwd.setTokenId(tokenId);
		sysUserFpwd.setActiveTime(DateUtils.addHours(new Date(), 1)); // 当前时间往后延一个小时
		sysUserFpwd.setEmailFlag("1");
		sysUserFpwd.setDelFlag("0");
		sysUserFpwd.setCreateBy(user);
		sysUserFpwd.setCreateDate(new Date());
		sysUserFpwd.setUpdateBy(user);
		sysUserFpwd.setUpdateDate(new Date());

		save(sysUserFpwd);
	}

	/**
	 * 通过userId删除忘记密码表中行(重置密码后要把该条数据删除)
	 *
	 * @param user
	 *            User
	 * @author YJH
	 */
	@Transactional(readOnly = false)
	public void deleteByUserId(User user) {
		dao.deleteByUserId(user.getId());
	}
}