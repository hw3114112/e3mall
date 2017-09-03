package cn.my.e3mall.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rows;
	
	private List<SearchItem> itemList;
	private int totalPages;
	private int recourdCount;
	
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(int recourdCount) {
		this.recourdCount = recourdCount;
		countTotalPages();
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
		countTotalPages();
	}
	/**
	 * 满足条件自动计算 总页数
	 * @param recourdCount
	 */
	private void countTotalPages() {
		if (rows != 0 && recourdCount != 0) {
			this.totalPages = (recourdCount + rows - 1) / rows;
		}
	}
}
