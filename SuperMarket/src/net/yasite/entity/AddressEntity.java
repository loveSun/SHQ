package net.yasite.entity;

public class AddressEntity {
	private String region_id;
	private String parent_id;
	private String region_name;
	private String region_type;
	private String agency_id;
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getRegion_type() {
		return region_type;
	}
	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}
	public String getAgency_id() {
		return agency_id;
	}
	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}
}
