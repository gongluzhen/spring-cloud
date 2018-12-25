package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserToken;
import com.sapit.springcloud.server.sys.dao.SysUserTokenDao;

@Service
@Transactional(readOnly = true)
public class SysUserTokenService extends CrudService<SysUserTokenDao, SysUserToken> {

    public SysUserToken get(String id) {
        return super.get(id);
    }

    public List<SysUserToken> findList(SysUserToken sysUserToken) {
        return super.findList(sysUserToken);
    }

    public Page<SysUserToken> findPage(SysUserToken sysUserToken) {
        return super.findPage(sysUserToken);
    }

    @Transactional(readOnly = false)
    public SysUserToken save(SysUserToken sysUserToken) {
        return super.save(sysUserToken);
    }

    @Transactional(readOnly = false)
    public void delete(SysUserToken sysUserToken) {
        super.delete(sysUserToken);
    }

    public List<SysUserToken> findAllList(SysUserToken sysUserToken) {
        return this.dao.findAllList(sysUserToken);
    }
}