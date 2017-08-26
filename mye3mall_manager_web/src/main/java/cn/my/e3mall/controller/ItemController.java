package cn.my.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.service.ItemService;

/**
 * 
 * @author hw311
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 分页显示商品列表 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public DataGridResult itemList(Integer page, Integer rows) {
		DataGridResult result = itemService.getItemsPage(page, rows);
		return result;
	}
	
}
