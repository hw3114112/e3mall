package cn.my.e3mall.item.pojo;

import cn.my.e3mall.pojo.TbItem;

public class Item extends TbItem {
	private static final long serialVersionUID = 1L;

	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	public Item(){}
	public String[] getImages() {
		String image = this.getImage();
		if (image != null && !"".equals(image)) {
			return image.split(",");
		}
		return null;
	}
}
