package cn.my.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.my.e3mall.common.pojo.E3Result;
import cn.my.e3mall.search.mapper.ItemSearchMapper;
import cn.my.e3mall.search.pojo.SearchItem;
import cn.my.e3mall.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemSearchMapper itemSearchMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public E3Result importItmes() {
		try {
			// 查询商品列表
			List<SearchItem> itemList = itemSearchMapper.getItemList();
			// 导入索引库
			for (SearchItem searchItem : itemList) {
				// 创建文档对象
				solrAddItemDocument(searchItem,false);
			}
			// 提交
			solrServer.commit();
			// 返回成功
			return E3Result.ok();

		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "商品导入失败");
		}

	}

	@Override
	public void solrAddItemDocument(SearchItem searchItem,boolean autoCommit) throws Exception {
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		// 写入索引库
		solrServer.add(document);
		if (autoCommit)
			solrServer.commit();
	}

	@Override
	public void solrAddItemDocument(SearchItem searchItem) throws Exception {
		this.solrAddItemDocument(searchItem, true);
	}
}
