package net.yasite.api.params;

import org.apache.http.entity.HttpEntityWrapper;

public class Address {
	private String consignee;
	int country;
	int province;
	int city;
	int district;
	String address;
	String tel;
	String mobile;
	String email;
	public Address(String consignee, int country, int province, int city,
			int district, String address, String tel, String mobile,
			String email) {
		super();
		this.consignee = consignee;
		this.country = country;
		this.province = province;
		this.city = city;
		this.district = district;
		this.address = address;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
	}
}
