package cn.my.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
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
	 * 分页显示商品列表
	 * 
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

	/**
	 * 新增一个 商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result saveItem(TbItem item, String desc) {
		E3Result result = itemService.addItem(item, desc);
		return result;
	}

	/**
	 * 商品编辑页面的 商品描述回显
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public E3Result getItemDesc(@PathVariable Long itemId) {
		return itemService.getItemDescByItemId(itemId);
	}

	/**
	 * 更新商品及描述
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result itemUpdate(TbItem item, String desc) {
		return itemService.updateItem(item, desc);
	}
	
	@RequestMapping("/rest/item/{action}")
	@ResponseBody
	public E3Result changeItemsStatus(String ids, @PathVariable String action) {
		// '商品状态，1-正常，2-下架，3-删除'
		Byte status= 3;
		if ("reshelf".equals(action)) {
			status = 1;
		}else if ("instock".equals(action)) {
			status = 2;
		}
		return itemService.batchChangeItemsStatusById(ids, status);
	}
}
