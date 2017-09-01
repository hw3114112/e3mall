package cn.my.e3mall.content.service;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.pojo.TbContent;

public interface ContentService {

	/**
	 * 根据类别ID 分页查询内容
	 * @param categoryId
	 * @param rows 
	 * @param page 
	 * @return easyUI需要的 DataGrid 
	 */
	DataGridResult findContentListByCid(Long categoryId, Integer page, Integer rows);

	/**
	 * 新增 内容
	 * @param content
	 * @return
	 */
	E3Result addOne(TbContent content);

	/**
	 * 修改 内容
	 * @param content
	 * @return
	 */
	E3Result editOne(TbContent content);

	/**
	 * 批量删除 内容
	 * @param ids
	 * @return
	 */
	E3Result batchDelete(String ids);

}
