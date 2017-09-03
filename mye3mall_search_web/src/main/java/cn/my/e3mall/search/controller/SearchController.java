package cn.my.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.my.e3mall.search.pojo.SearchResult;
import cn.my.e3mall.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	@Value("${search.result.rows}")
	private Integer rows;

	@RequestMapping("search")
	public String search(String keyword, @RequestParam(defaultValue = "1") int page, Model model) throws Exception {
		SearchResult result;
		result = searchService.search(keyword, page, rows);
		model.addAttribute("query", keyword);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getRecourdCount());
		model.addAttribute("itemList", result.getItemList());
		return "search";
	}
}
