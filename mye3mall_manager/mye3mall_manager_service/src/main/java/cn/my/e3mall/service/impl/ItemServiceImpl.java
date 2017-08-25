package cn.my.e3mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.mapper.TbItemMapper;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.pojo.TbItemExample;
import cn.my.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public DataGridResult getItemsPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		Page<TbItem> list = (Page<TbItem>) itemMapper.selectByExample(new TbItemExample());
		return new DataGridResult(list.getTotal(),list.getResult());
	}

}
