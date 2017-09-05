package cn.my.e3mall.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.my.e3mall.search.mapper.ItemSearchMapper;
import cn.my.e3mall.search.pojo.SearchItem;
import cn.my.e3mall.search.service.SearchItemService;

@Service
public class AddItemMessageListener implements MessageListener {

	@Autowired
	private ItemSearchMapper itemSearchMapper;
	@Autowired
	private SearchItemService searchItemService;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			long id = Long.parseLong(textMessage.getText());
			//等待事务
			Thread.sleep(1000);
			System.out.println(id);
			SearchItem searchItem = itemSearchMapper.getItemById(id);
			searchItemService.solrAddItemDocument(searchItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
