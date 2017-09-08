package cn.my.e3mall.sso.service;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.pojo.TbUser;

/**
 * 单点登录系统, 用户管理服务
 * @author hw311
 *
 */
public interface UserService {

	/**
	 * 注册信息 不可重复性校验
	 * @param data
	 * @param type
	 * @return
	 */
	E3Result checkData(String data, Integer type);

	/**
	 * 新注册用户
	 * @param user
	 * @return
	 */
	E3Result addUser(TbUser user);

	/**
	 * 登录 查询用户 并将用户信息保存到redis中
	 * @param username
	 * @param password
	 */
	E3Result login(String username, String password);

	/**
	 * 根据token 查询redis
	 * @param token
	 * @return
	 */
	E3Result getUserByToken(String token);

	/**
	 * 退出
	 * @param token
	 */
	void logout(String token);

}
