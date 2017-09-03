package cn.my.e3mall.search.service;

import cn.my.e3mall.search.pojo.SearchResult;

/**
 * 商品搜索 服务接口
 * @author hw311
 *
 */
public interface SearchService {

	/**
	 * 根据关键字 分页查询 商品索引
	 * @param keyWord
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	SearchResult search(String keyWord, int page, int rows) throws Exception;
}
