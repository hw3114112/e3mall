package cn.my.e3mall.protal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.my.e3mall.content.service.ContentService;
import cn.my.e3mall.pojo.TbContent;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("index")
	public String index(Model model) {
		List<TbContent> ad1List = contentService.findByCid(89L);
		model.addAttribute("ad1List", ad1List);
		return "index";
	}
	
	
}
