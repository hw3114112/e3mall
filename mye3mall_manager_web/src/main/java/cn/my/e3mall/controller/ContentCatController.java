package cn.my.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.pojo.TreeNode;
import cn.my.e3mall.content.service.ContentCatService;
import cn.my.e3mall.pojo.TbContentCategory;

@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> listByPid(Long id) {
		if (null == id) 
			id = 0L;
		return contentCatService.findContentCatListByParentId(id);
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addOne(TbContentCategory contentCategory) {
		return contentCatService.addOne(contentCategory);
	}
	
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public E3Result deleteOne(Long id) {
		return contentCatService.deleteById(id);
	}
	
	@RequestMapping("/content/category/update/")
	@ResponseBody
	public E3Result updateOne(TbContentCategory contentCategory) {
		return contentCatService.updateNameById(contentCategory);
	}
}
