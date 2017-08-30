package cn.my.e3mall.controller;

import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.my.e3mall.common.utils.FastDFSClient;
import cn.my.e3mall.common.utils.JsonUtils;

@Controller
@RequestMapping("/pic")
public class PictureController {

	@Value("${imageserver.url}")
	private String imageserverUrl;
	/**
	 * 接收上传图片
	 * @return 成功返回图片地址, 失败返回错误信息
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile uploadFile) {
		String filename = uploadFile.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename);
		byte[] bytes;
		HashMap<String, Object> map = new HashMap<>();
		try {
			bytes = uploadFile.getBytes();
			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
			String uri = client.uploadFile(bytes, extension);
			String url =imageserverUrl+uri;
			//TODO 保存图片路径
			
			map.put("error", 0);
			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "文件上传出错");
		}
		String json = JsonUtils.objectToJson(map);
		return json;
	}
}
