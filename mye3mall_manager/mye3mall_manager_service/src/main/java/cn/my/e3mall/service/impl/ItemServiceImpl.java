package cn.my.e3mall.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.pojo.IDUtils;
import cn.my.e3mall.mapper.TbItemDescMapper;
import cn.my.e3mall.mapper.TbItemMapper;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.pojo.TbItemDesc;
import cn.my.e3mall.pojo.TbItemExample;
import cn.my.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem getItemById(long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public DataGridResult getItemsPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		Page<TbItem> list = (Page<TbItem>) itemMapper.selectByExample(new TbItemExample());
		return new DataGridResult(list.getTotal(), list.getResult());
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		// 1、生成商品id
		// 实现方案：
		// c) 可以直接去毫秒值+随机数。可以使用。
		// d) 使用redis。Incr。推荐使用。
		// 使用IDUtils生成商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 2、补全TbItem对象的属性
		// '商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		item.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		// 7、E3Result.ok()
		return E3Result.ok();
	}

	@Override
	public E3Result getItemDescByItemId(Long itemId) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return E3Result.ok(itemDesc);
	}

	@Override
	public E3Result updateItem(TbItem item, String desc) {
		// 添加更新时间
		Date date = new Date();
		item.setUpdated(date);
		// 更新商品item
		itemMapper.updateByPrimaryKeySelective(item);
		// 创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 补全TbItemDesc的属性
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		// 更新商品描述
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return E3Result.ok();
	}

	@Override
	public E3Result batchChangeItemsStatusById(String ids, Byte status) {
		try {
			// 创建一个空的item用于更新
			TbItem item = new TbItem();
			// '商品状态，1-正常，2-下架，3-删除'
			item.setStatus(status);
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				item.setId(new Long(id));
				itemMapper.updateByPrimaryKeySelective(item);
			}
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, e.getMessage());
		}
	}

}
