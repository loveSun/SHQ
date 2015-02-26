package net.yasite.model;

import java.util.List;

import net.yasite.api.params.Address;
import net.yasite.entity.AddressDataEntity;
import net.yasite.entity.AddressInfoEntity;
import net.yasite.entity.ClassifyDataEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.entity.MyAddressDataEntity;
import net.yasite.entity.UserCarDataEntity;
import net.yasite.entity.UserCarDatasEntity;
import net.yasite.entity.UserInfoData;
import net.yasite.service.GoodsService;
import android.content.Context;

public class GoodsListModel extends Model {
	GoodsService service;

	public GoodsListModel(Context context) {
		this.context = context;
		service = new GoodsService(context);
	}

	public GoodListEntity getGoodList(int page) {
		return service.getGoodsList(page);
	}

	public GoodListEntity getData(String name, int page) {
		return service.getRequestData(name, page);
	}

	public GoodListEntity getGoodsDetail(int id) {
		return service.getGoodsDetail(id);
	}

	public UserInfoData getRegist(String name, String pass) {
		return service.getRegist(name, pass);
	}

	public UserInfoData getLogin(String name, String pass) {
		return service.getLogin(name, pass);
	}

	public UserCarDatasEntity getCar(String token, String user_id,
			int goods_id, String goods_sn, String goods_name,
			float market_price, float goods_price, int goods_number) {
		return service.carGoods(token, user_id, goods_id, goods_sn, goods_name,
				market_price, goods_price, goods_number);
	}

	public ClassifyDataEntity getClassify(int id) {
		return service.getClassify(id);
	}

	public UserCarDataEntity getCarGoodsList(String id, String token) {
		return service.getCarGoodsList(id, token);
	}

	public void updateCar(String token, int rec_id, String user_id,
			int goods_id, String goods_sn, String goods_name,
			float market_price, float goods_price, int goods_number) {
		service.updateCarGoods(token, rec_id, user_id, goods_id, goods_sn,
				goods_name, market_price, goods_price, goods_number);
	}

	public void delCarGoods(String token, String rec_id, String user_id) {
		service.delCarGoods(token, rec_id, user_id);
	}

	public UserInfoData ownMsg(int user_id, String username, String token) {
		return service.ownMsg(user_id, username, token);
	}

	public void updateUserInfo(String id, String token, String name,
			String sex, String qq, String mobile_phone, String email) {
		service.updateUserInfo(id, token, name, sex, qq, mobile_phone, email);
	}

	public AddressDataEntity getAddress(String parent_id) {
		return service.getAddress(parent_id);
	}

	public void addAddress(String token, String consignee, String email,
			int user_id, int country, int province, int city, int district,
			String address, String tel, String mobile) {
		service.addAddress(token, consignee, email, user_id, country, province,
				city, district, address, tel, mobile);
	}
	public MyAddressDataEntity getAddressList(String user_id,String token){
		return service.getAddressList(user_id, token);
	}
	public void deleteAddress(String token,String address_id){
		service.deleteAddress(token, address_id);
	}
	public AddressInfoEntity addressInfo(String token,String user_id,String address_id){
		return service.addressInfo(token, user_id, address_id);
	}
	public void updateAddress(String token, String id, String consignee,
			String email, int user_id, int country, int province, int city,
			int district, String address, String tel, String mobile) {
		service.updateAddress(token, id, consignee, email, user_id, country, province, city, district, address, tel, mobile);
	}
	public void head(String user_id,String token,String f1){
		service.head(user_id, token, f1);
	}
	public void createOrder(String user_id,Address address){
		service.createOrder(user_id, address);
	}
	public void getOrderList(String user_id){
		service.getOrderList(user_id);
	}
}
