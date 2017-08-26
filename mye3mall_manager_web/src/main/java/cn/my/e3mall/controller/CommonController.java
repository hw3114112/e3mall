package cn.my.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通用请求跳转
 * @author hw311
 *
 */
@Controller
public class CommonController {
	
	/**
	 * 显示首页
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * 显示标签页
	 * @return
	 */
	@RequestMapping("/{page}")
	public String toTagPage(@PathVariable String page) {
		return page;
	}
	
}
