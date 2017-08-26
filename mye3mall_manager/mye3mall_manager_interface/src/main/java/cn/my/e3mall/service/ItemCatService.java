package cn.my.e3mall.service;

import java.util.List;

import cn.my.e3mall.common.pojo.TreeNode;
/**
 * 商品分类 服务接口
 * @author hw311
 *
 */
public interface ItemCatService {

	/**
	 * 根据上级节点ID查询下一级节点
	 * @param parentId
	 * @return List<TreeNode> Tree节点的包装类集合
	 */
	List<TreeNode> findItemCatListByParentId(long parentId);

}
