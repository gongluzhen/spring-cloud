package com.sapit.springcloud.server.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.User;

@MyBatisDao
public interface UserDao extends CrudDao<User> {

	/**
	 * 根据登录名称查询用户
	 *
	 * @param user
	 *            User
	 * @return User
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 *
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

	/**
	 * 查询全部用户数目
	 *
	 * @return
	 */
	public long findAllCount(User user);

	/**
	 * 更新用户密码
	 *
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);

	/**
	 * 更新登录信息，如：登录IP、登录时间
	 *
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 *
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);

	/**
	 * 插入用户角色关联数据
	 *
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

	/**
	 * 更新用户信息
	 *
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	public int updateAccountBalanceById(User user);

	/**
	 * 查询身份号是否有重复的
	 *
	 * @param user
	 *            User
	 * @return List<User>
	 * @author YJH
	 */
	List<User> findIdNumberRepeat(User user);

	/**
	 * 微信获取信息 根据课程id
	 *
	 * @param id
	 *            课程id
	 * @return 人员集合
	 */
	List<User> getWcUserList(String id);

	/**
	 * 根据会员身份证号获取会员信息
	 *
	 * @param idNumber
	 *            身份证
	 * @return 人员实体
	 */
	public User getUserByIdNumber(String idNumber);

	/**
	 * 修改密码
	 *
	 * @param password
	 *            密码
	 * @param id
	 *            id
	 */
	public void updateUserPassword(@Param("password") String password, @Param("id") String id);

	/**
	 * 通过登录名和邮件查找用户（忘记密码功能）
	 *
	 * @param user
	 *            User
	 * @return List<User>
	 * @author YJH
	 */
	public List<User> findUserByLoginNameAndEmail(User user);

	/**
	 * 删除用户角色通过角色id
	 *
	 * @param roleId
	 *            角色id
	 * @author YJH
	 */
	public void deleteUserRoleByRoleId(String roleId);

	public void deleteUserRoleByRoleIdUserId(@Param("roleId") String roleId, @Param("userId") String userId);

	public void insertUserRoleByRoleIdUserId(@Param("roleId") String roleId, @Param("userId") String userId);
}
