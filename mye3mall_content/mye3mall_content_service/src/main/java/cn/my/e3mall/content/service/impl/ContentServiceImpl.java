package cn.my.e3mall.content.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.content.service.ContentService;
import cn.my.e3mall.mapper.TbContentMapper;
import cn.my.e3mall.pojo.TbContent;
import cn.my.e3mall.pojo.TbContentExample;
import cn.my.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	TbContentMapper contentMapper;

	@Override
	public DataGridResult findContentListByCid(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		Page<TbContent> list = (Page<TbContent>) contentMapper.selectByExampleWithBLOBs(example);
		return new DataGridResult(list.getTotal(), list.getResult());
	}

	@Override
	public E3Result addOne(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		return E3Result.ok();
	}

	@Override
	public E3Result editOne(TbContent content) {
		Date date = new Date();
		content.setUpdated(date);
		contentMapper.updateByPrimaryKeySelective(content);
		return E3Result.ok();
	}

	@Override
	public E3Result batchDelete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			contentMapper.deleteByPrimaryKey(new Long(id));
		}
		return E3Result.ok();
	}

}