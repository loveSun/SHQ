package net.yasite.api.params;

import org.json.JSONObject;

import net.yasite.api.BaseAPI;

public class Addressparams extends BaseHttpParam {

	private String parent_id;

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	

}
