package cn.my.e3mall.content.service;

import java.util.List;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.pojo.TreeNode;
import cn.my.e3mall.pojo.TbContentCategory;

public interface ContentCatService {

	/**
	 * 根据上级节点ID查询下一级节点
	 * @param id
	 * @return
	 */
	List<TreeNode> findContentCatListByParentId(Long id);

	/**
	 * 添加一个 内容分类
	 * @param contentCategory
	 * @return
	 */
	E3Result addOne(TbContentCategory contentCategory);

	/**
	 * 删除一个内容分类
	 * @param id
	 * @return
	 */
	E3Result deleteById(Long id);

	/**
	 * 修改分类名称
	 * @param contentCategory
	 * @return
	 */
	E3Result updateNameById(TbContentCategory contentCategory);

}
