package cn.my.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.pojo.TreeNode;
import cn.my.e3mall.content.service.ContentCatService;
import cn.my.e3mall.mapper.TbContentCategoryMapper;
import cn.my.e3mall.pojo.TbContentCategory;
import cn.my.e3mall.pojo.TbContentCategoryExample;
import cn.my.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TreeNode> findContentCatListByParentId(Long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List list = contentCategoryMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			TbContentCategory contentCat = (TbContentCategory) list.get(i);
			TreeNode treeNode = new TreeNode();
			treeNode.setId(contentCat.getId());
			treeNode.setText(contentCat.getName());
			treeNode.setState(contentCat.getIsParent() ? "closed" : "open");
			list.set(i, treeNode);
		}
		return list;
	}

	@Override
	public E3Result addOne(TbContentCategory contentCategory) {
		// 1 补全TbContentCategory对象的属性
		// 新建节点不为父
		contentCategory.setIsParent(false);
		// 默认排序都是1
		contentCategory.setSortOrder(1);
		// '状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		// 2 向tb_content_category表中插入数据
		contentCategoryMapper.insert(contentCategory);
		// 3 更新父节点status状态
		TbContentCategory parentNode = new TbContentCategory();
		parentNode.setIsParent(true);
		parentNode.setId(contentCategory.getParentId());
		contentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result deleteById(Long id) {
		// 如果删除的是父节点，子节点要级联删除。
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 两种解决方案：
		// 1）如果判断是父节点不允许删除。
		/**
		if (contentCategory.getIsParent()) {
			return E3Result.build(500, "不可以直接删除父节点");
		}
		*/
		// 2）递归删除。√
		this.recursiveDelete(contentCategory);
		// 2、判断父节点下是否还有子节点，
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		Long parentId = contentCategory.getParentId();
		criteria.andParentIdEqualTo(parentId);
		int brothers = contentCategoryMapper.countByExample(example);
		// 如果没有需要把父节点的isparent改为false
		if (0 == brothers) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(parentId);
			parent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(parent);
		}
		return E3Result.ok();
	}

	/**
	 * 递归删除 商品分类的 父节点
	 * @param contentCategory
	 */
	private void recursiveDelete(TbContentCategory contentCategory) {
		// 是父节点 递归
		if (contentCategory.getIsParent()) {
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(contentCategory.getId());
			List<TbContentCategory> childrenList = contentCategoryMapper.selectByExample(example);
			if (null != childrenList) {
				for (TbContentCategory children : childrenList) {
					this.recursiveDelete(children);
				}
			}
		}
		// 1、根据id删除记录。物理删除
		contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
	}

	@Override
	public E3Result updateNameById(TbContentCategory contentCategory) {
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok();
	}

}
