package cn.my.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.utils.CookieUtils;
import cn.my.e3mall.pojo.TbUser;
import cn.my.e3mall.sso.service.UserService;

/**
 * 单点登录系统 页面跳转和 user管理 Controller
 * 
 * @author hw311
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Value("${cookie.maxage}")
	private int cookieMaxage;

	/**
	 * 跳转登录 注册等页面
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("/page/{page}")
	public String showRegister(@PathVariable String page) {
		return page;
	}

	/**
	 * 数据有效性校验
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check/{data}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String data, @PathVariable Integer type) {
		E3Result e3Result = userService.checkData(data, type);
		return e3Result;
	}

	/**
	 * 注册新用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user) {
		E3Result result = userService.addUser(user);
		return result;
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		E3Result e3Result = userService.login(username, password);
		// 拿到token, 写入cookie
		String token = e3Result.getData().toString();
		CookieUtils.setCookie(request, response, "token", token, cookieMaxage);
		return e3Result;
	}

	/**
	 * 获得缓存中的token
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result result = userService.getUserByToken(token);
		if (StringUtils.hasText(callback)) {
			// jsonp数据格式
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}

	/**
	 * 安全退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 拿到cookie中的 token
		String token = CookieUtils.getCookieValue(request, "token");
		// 清除redis中的token
		userService.logout(token);
		// 清除cookie中的token
		CookieUtils.deleteCookie(request, response, "token");
		// 拿到请求来源
		String referer = request.getHeader("Referer");
		// 重定向
		return "redirect:" + referer;
	}
}