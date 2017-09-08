package cn.my.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.my.e3mall.item.pojo.Item;
import cn.my.e3mall.pojo.TbItem;
import cn.my.e3mall.pojo.TbItemDesc;
import cn.my.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 监听商品添加消息，生成静态页面
 * 
 * @author hw311
 *
 */
@Service
public class HtmlGenMessageListener implements MessageListener {

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${html.gen.path}")
	private String htmlGenPath;

	@Override
	public void onMessage(Message message) {
		// 2、从FreeMarkerConfigurer对象中获得Configuration对象。
		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			// 3、使用Configuration对象获得Template对象。注意, 直接写相对于FreeMakerConfigurer的配置属性的路径
			Template template = configuration.getTemplate("item.ftl");
			// 4、创建数据集
			HashMap<String, Object> data = new HashMap<>();
			// 4.1 从消息中获得id
			TextMessage textMessage = (TextMessage) message;
			Long itemId = Long.parseLong(textMessage.getText());
			// 4.2 查询数据库
			Thread.sleep(1000);
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			// 5、创建输出文件的Writer对象。
			FileWriter fileWriter = new FileWriter(htmlGenPath+itemId+".html");
			// 6、调用模板对象的process方法，生成文件。
			template.process(data, fileWriter);
			// 7、关闭流。
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
