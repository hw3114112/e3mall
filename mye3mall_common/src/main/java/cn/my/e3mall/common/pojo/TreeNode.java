package cn.my.e3mall.common.pojo;

import java.io.Serializable;

public class TreeNode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

    private String text;

    private String state;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getState() {
		return state;
	}

	public final void setState(String state) {
		this.state = state;
	}
}
