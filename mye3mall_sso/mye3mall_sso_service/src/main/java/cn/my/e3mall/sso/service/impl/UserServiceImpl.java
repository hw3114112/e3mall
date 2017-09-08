package cn.my.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import cn.my.e3mall.common.jedis.JedisClient;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.utils.JsonUtils;
import cn.my.e3mall.mapper.TbUserMapper;
import cn.my.e3mall.pojo.TbUser;
import cn.my.e3mall.pojo.TbUserExample;
import cn.my.e3mall.pojo.TbUserExample.Criteria;
import cn.my.e3mall.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${session.maxage}")
	private int sessionMaxage;
	/**
	 * reids中的 token前缀
	 */
	private static final String TOKEN = "token:";
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public E3Result checkData(String data, Integer type) {
		if (type != 1 && type != 2 && type != 3) {
			return E3Result.build(400, "非法的参数");
		}
		// 2、查询条件根据参数动态生成。
		int num = countUserByDataType(data, type);
		// 5、使用e3Result包装，并返回。
		// 3、判断查询结果，如果查询到数据返回false。
		if (num > 0) {
			return E3Result.ok(false);
		}
		// 4、如果没有返回true。
		return E3Result.ok(true);
	}

	/**
	 * 不可重复性校验
	 * 
	 * @param data
	 *            注册信息
	 * @param type
	 *            信息类型
	 * @return
	 */
	private int countUserByDataType(String data, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(data);
		} else if (type == 3) {
			criteria.andEmailEqualTo(data);
		}
		// 1、从tb_user表中查询数据
		int num = userMapper.countByExample(example);
		return num;
	}

	@Override
	public E3Result addUser(TbUser user) {
		// 1、使用TbUser接收提交的请求。
		// 1.1 注册信息 不为空校验
		if (!StringUtils.hasText(user.getUsername())) {
			return E3Result.build(400, "用户名不能为空");
		}
		if (!StringUtils.hasText(user.getPassword())) {
			return E3Result.build(400, "密码不能为空");
		}
		if (!StringUtils.hasText(user.getPhone())) {
			return E3Result.build(400, "手机号不能为空");
		}
		// 1.2 注册信息 不可重复性校验
		int num = countUserByDataType(user.getUsername(), 1);
		if (num > 0) {
			return E3Result.ok(false);
		}
		num = countUserByDataType(user.getPhone(), 2);
		if (num > 0) {
			return E3Result.ok(false);
		}
		// 2、补全TbUser其他属性。
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		// 3、密码要进行MD5加密。
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		// 4、把用户信息插入到数据库中。
		userMapper.insert(user);
		// 5、返回e3Result。
		return E3Result.ok();
	}

	@Override
	public E3Result login(String username, String password) {
		// 1、判断用户名密码是否正确。
		if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
			return E3Result.build(400, "用户名或密码不能为空");
		}
		// 1.1 根据用户名获得对象
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return E3Result.build(400, "用户名错误");
		}
		TbUser user = list.get(0);
		// 1.2 校验密码
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return E3Result.build(400, "密码错误");
		}
		// 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
		String token = UUID.randomUUID().toString();
		// 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
		// user 对象中的密码应删除
		user.setPassword(null);
		// 4、使用hash类型保存Session信息。可以使用“前缀:token”为key
		jedisClient.hset(TOKEN + token, "user", JsonUtils.objectToJson(user));
		// 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
		jedisClient.expire(TOKEN + token, sessionMaxage);
		// 6、返回e3Result包装token。
		return E3Result.ok(token);
	}

	@Override
	public E3Result getUserByToken(String token) {
		// 1、从url中取参数。
		// 2、根据token查询redis。
		String userJson = jedisClient.hget(TOKEN + token, "user");
		// 3、如果查询不到数据。返回用户已经过期。
		if (!StringUtils.hasText(userJson)) {
			return E3Result.build(400, "用户登录已经过期，请重新登录。");
		}
		// 4、如果查询到数据，说明用户已经登录。
		// 5、需要重置key的过期时间。
		jedisClient.expire(TOKEN + token, sessionMaxage);
		// 6、把json数据转换成TbUser对象，然后使用e3Result包装并返回。
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		return E3Result.ok(user);
	}

	@Override
	public void logout(String token) {
		jedisClient.del(TOKEN+token);
	}
}