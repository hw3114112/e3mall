package cn.my.e3mall.service;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.pojo.TbItem;
/**
 * 商品 service层接口
 * @author hw311
 *
 */
public interface ItemService {
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	TbItem getItemById(long id);
	
	/**
	 * 获得分页数据
	 * @param page 第几页,从1开始
	 * @param rows 一页的容量
	 * @return
	 */
	DataGridResult getItemsPage(Integer page, Integer rows);

}
