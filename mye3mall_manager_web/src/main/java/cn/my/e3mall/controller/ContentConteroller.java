package cn.my.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.content.service.ContentService;
import cn.my.e3mall.pojo.TbContent;

@Controller
public class ContentConteroller {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult listByCid(Long categoryId, Integer page, Integer rows) {
		return contentService.findContentPageByCid(categoryId, page, rows);
	}

	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addOne(TbContent content) {
		return contentService.addOne(content);
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editOne(TbContent content) {
		return contentService.editOne(content);
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result delete(String ids) {
		return contentService.batchDelete(ids);
	}
}
