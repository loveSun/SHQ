package net.yasite.entity;

public class GoodsCarEntity {
	private int id;
	private String goods_id;
	private String image;
	private String user;
	private String name;
	private String price;
	private String user_id;
	public GoodsCarEntity(int id, String goods_id, String image, String user,
			String name, String price) {
		super();
		this.id = id;
		this.goods_id = goods_id;
		this.image = image;
		this.user = user;
		this.name = name;
		this.price = price;
	}
	
	public GoodsCarEntity(String goods_id, String image, String user,
			String name, String price, String user_id) {
		super();
		this.goods_id = goods_id;
		this.image = image;
		this.user = user;
		this.name = name;
		this.price = price;
		this.user_id = user_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
