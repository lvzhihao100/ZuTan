package com.eqdd.common.bean;


import java.io.Serializable;
import java.util.List;

public class BaseBean<T> implements Serializable {
	 int status;
	 String msg;
	 List<T> items;

	@Override
	public String toString() {
		return "BaseBean{" +
				"status=" + status +
				", msg='" + msg + '\'' +
				", items=" + items +
				'}';
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
