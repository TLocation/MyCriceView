package com.gameplayer.mycriceview.data;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 17:01
 * description：
 */

public class DataBean {
	private int id;

	private String message;

	private boolean isSelect;


	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public DataBean(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
