package cn.my.e3mall.search.mapper;

import java.util.List;

import cn.my.e3mall.search.pojo.SearchItem;
/**
 * 商品查询结果包装类
 * @author hw311
 *
 */
public interface ItemSearchMapper {

	/**
	 * 查询所有商品数据
	 * @return
	 */
	List<SearchItem> getItemList();
}
