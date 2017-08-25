package cn.my.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.service.ItemService;

/**
 * 
 * @author hw311
 *
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
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
	 * 分页显示商品列表 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult itemList(Integer page, Integer rows) {
		DataGridResult result = itemService.getItemsPage(page, rows);
		return result;
	}
	
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
}
