package cn.my.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.my.e3mall.common.pojo.TreeNode;
import cn.my.e3mall.mapper.TbItemCatMapper;
import cn.my.e3mall.pojo.TbItemCat;
import cn.my.e3mall.pojo.TbItemCatExample;
import cn.my.e3mall.pojo.TbItemCatExample.Criteria;
import cn.my.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<TreeNode> findItemCatListByParentId(long id) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		ArrayList<TreeNode> nodes = new ArrayList<>(list.size());
		for (TbItemCat tbItemCat : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbItemCat.getId());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			nodes.add(treeNode);
		}
		return nodes;
	}

}
