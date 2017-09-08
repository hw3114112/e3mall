package cn.my.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.my.e3mall.item.pojo.Item;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.pojo.TbItemDesc;
import cn.my.e3mall.service.ItemService;

/**
 * 商品详情动态页面 Controller
 * @author hw311
 *
 */
@Controller
public class ItemInfoController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}