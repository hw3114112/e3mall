package cn.my.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.TreeNode;
import cn.my.e3mall.service.ItemCatService;
/**
 * 商品分类管理
 * @author hw311
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> listByPid(Long id) {
		if (null == id) 
			id = 0L;
		return itemCatService.findItemCatListByParentId(id);
	}
}
