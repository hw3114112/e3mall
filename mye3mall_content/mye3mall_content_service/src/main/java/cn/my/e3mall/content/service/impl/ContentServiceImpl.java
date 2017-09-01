package cn.my.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.my.e3mall.common.jedis.JedisClient;
import cn.my.e3mall.common.pojo.DataGridResult;
import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.common.utils.JsonUtils;
import cn.my.e3mall.content.service.ContentService;
import cn.my.e3mall.mapper.TbContentMapper;
import cn.my.e3mall.pojo.TbContent;
import cn.my.e3mall.pojo.TbContentExample;
import cn.my.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	/**
	 * redis中的保存内容的 hash 的key
	 */
	private static final String CONTENT_KEY = "CONTENT_KEY";
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public DataGridResult findContentPageByCid(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<TbContent> contents = this.findByCidFromDatabase(categoryId);
		Page<TbContent> list = (Page<TbContent>) contents;
		return new DataGridResult(list.getTotal(), list.getResult());
	}

	@Override
	public E3Result addOne(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		clearCache(content);
		return E3Result.ok();
	}

	/**
	 * 更新操作清除对应缓存
	 * @param content
	 */
	private void clearCache(TbContent content) {
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
	}

	@Override
	public E3Result editOne(TbContent content) {
		Date date = new Date();
		content.setUpdated(date);
		contentMapper.updateByPrimaryKeySelective(content);
		clearCache(content);
		return E3Result.ok();
	}

	@Override
	public E3Result batchDelete(String ids) {
		String[] idArray = ids.split(",");
		TbContent content = contentMapper.selectByPrimaryKey(new Long(idArray[0]));
		for (String id : idArray) {
			contentMapper.deleteByPrimaryKey(new Long(id));
		}
		clearCache(content);
		return E3Result.ok();
	}

	@Override
	public List<TbContent> findByCid(Long categoryId) {
		// 查询缓存
		try {
			String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
			// 判断json是否为空
			if (StringUtils.hasText(json)) {
				// 把json转换成list
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TbContent> list = findByCidFromDatabase(categoryId);
		// 向缓存中添加数据
		try {
			jedisClient.hset(CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 直接从数据库查询内容, 不过缓存
	 * 
	 * @param categoryId
	 * @return
	 */
	private List<TbContent> findByCidFromDatabase(Long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
		return contents;
	}

}