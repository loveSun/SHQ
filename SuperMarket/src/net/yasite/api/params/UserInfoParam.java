package net.yasite.api.params;

public class UserInfoParam extends BaseHttpParam {

	private String token;
	private String user_id;
	private String user_name;
	private String address_id;
	private String f1;
	
	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
