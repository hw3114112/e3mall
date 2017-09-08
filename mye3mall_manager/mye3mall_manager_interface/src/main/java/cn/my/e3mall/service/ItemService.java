package cn.my.e3mall.service;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.pojo.TbItemDesc;
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

	/**
	 * 添加一个商品
	 * @param item 商品表对应的pojo类
	 * @param desc 商品描述
	 * @return
	 */
	E3Result addItem(TbItem item, String desc);

	/**
	 * 获得 商品描述
	 * @param itemId
	 * @return 商品描述
	 */
	TbItemDesc getItemDescByItemId(Long itemId);

	/**
	 * 更新商品数据及商品描述
	 * @param item
	 * @param desc
	 * @return
	 */
	E3Result updateItem(TbItem item, String desc);

	/**
	 * 批量更新商品状态
	 * @param ids
	 * @param status
	 * @return
	 */
	E3Result updateItemsStatusById(String ids, Byte status);

}
