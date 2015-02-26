package net.yasite.entity;

import java.io.Serializable;
import java.util.List;

public class MyAddressDataEntity implements Serializable{
	private List<MyAddressEntity> data;

	public List<MyAddressEntity> getData() {
		return data;
	}

	public void setData(List<MyAddressEntity> data) {
		this.data = data;
	}
	
}
