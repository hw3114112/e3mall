package cn.my.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用请求跳转
 * 
 * @author hw311
 *
 */
@Controller
public class PageController {

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

	/**
	 * 显示商品编辑页面
	 * @param 
	 * @return
	 */
	@RequestMapping("/rest/page/{page}")
	public String toEditItemPage(@PathVariable String page) {
		return page;
	}
}
