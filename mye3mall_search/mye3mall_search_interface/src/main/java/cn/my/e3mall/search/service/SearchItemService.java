package cn.my.e3mall.search.service;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.search.pojo.SearchItem;

/**
 * 商品信息导入索引库
 * @author hw311
 *
 */
public interface SearchItemService {
	/**
	 * 商品信息导入索引库
	 * @return
	 */
	E3Result importItmes();
	
	/**
	 * 添加一个 商品文档进 索引库
	 * @param searchItem 
	 * @param autoCommit 是否 自动提交
	 * @throws Exception
	 */
	void solrAddItemDocument(SearchItem searchItem,boolean autoCommit) throws Exception;
	
	/**
	 * 添加一个 商品文档进 索引库 自动提交
	 * @param searchItem
	 * @throws Exception
	 */
	void solrAddItemDocument(SearchItem searchItem) throws Exception;
}
