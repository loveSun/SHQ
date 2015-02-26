package net.yasite.entity;

import java.io.Serializable;
import java.util.List;

public class GoodListEntity implements Serializable {
	
	private List<GoodEntity> data;

	public List<GoodEntity> getData() {
		return data;
	}

	public void setData(List<GoodEntity> data) {
		this.data = data;
	}

	
	
}
