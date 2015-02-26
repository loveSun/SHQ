package net.yasite.service;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.OAEPParameterSpec;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import net.yasite.api.AddAddressApi;
import net.yasite.api.AddCarApi;
import net.yasite.api.AddressInfoApi;
import net.yasite.api.AddressListApi;
import net.yasite.api.AddressMSGApi;
import net.yasite.api.BaseAPI;
import net.yasite.api.CarGoodsListApi;
import net.yasite.api.ClassApi;
import net.yasite.api.CreateOrderApi;
import net.yasite.api.DelCarGoodsApi;
import net.yasite.api.DeleteAddressApi;
import net.yasite.api.GoodsDetailApi;
import net.yasite.api.GoodsListAPI;
import net.yasite.api.HeadApi;
import net.yasite.api.LoginApi;
import net.yasite.api.OrderListApi;
import net.yasite.api.OwnMSGApi;
import net.yasite.api.RegistApi;
import net.yasite.api.SelectByNameApi;
import net.yasite.api.UpdateAddressApi;
import net.yasite.api.UpdateCarApi;
import net.yasite.api.UpdateUserInfoApi;
import net.yasite.api.params.Address;
import net.yasite.api.params.Addressparams;
import net.yasite.api.params.ClassParam;
import net.yasite.api.params.GoodsListParam;
import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.AddressDataEntity;
import net.yasite.entity.AddressInfoEntity;
import net.yasite.entity.ClassifyDataEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.entity.MyAddressDataEntity;
import net.yasite.entity.UserCarDataEntity;
import net.yasite.entity.UserCarDatasEntity;
import net.yasite.entity.UserInfoData;
import android.content.Context;
import android.util.Log;

public class GoodsService extends BaseService {

	public GoodsService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GoodListEntity getGoodsList(int page) {
		GoodsListParam pm = new GoodsListParam();
		pm.setPage(Integer.toString(page));
		BaseAPI api = new GoodsListAPI(context, pm);
		try {
			if (api.doGet()) {
				return (GoodListEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public GoodListEntity getRequestData(String name, int page) {
		GoodsListParam pm = new GoodsListParam();
		pm.setName(name);
		pm.setPage(Integer.toString(page));
		BaseAPI api = new SelectByNameApi(context, pm);
		try {
			if (api.doGet()) {
				return (GoodListEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public GoodListEntity getGoodsDetail(int id) {
		GoodsListParam pm = new GoodsListParam();
		pm.setId(Integer.toString(id));
		BaseAPI api = new GoodsDetailApi(context, pm);
		try {
			if (api.doGet()) {
				return (GoodListEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public UserInfoData getRegist(String name, String pass) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("name", name));
		list.add(getValue("pwd", pass));
		BaseAPI api = new RegistApi(context, list);
		try {
			if (api.doPost()) {
				return (UserInfoData) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public UserInfoData getLogin(String name, String pass) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("name", name));
		list.add(getValue("pwd", pass));
		BaseAPI api = new LoginApi(context, list);
		try {
			if (api.doPost()) {
				return (UserInfoData) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public UserCarDatasEntity carGoods(String token, String user_id,
			int goods_id, String goods_sn, String goods_name,
			float market_price, float goods_price, int goods_number) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("user_id", user_id));
		list.add(getValue("goods_id", Integer.toString(goods_id)));
		list.add(getValue("goods_sn", goods_sn));
		list.add(getValue("goods_name", goods_name));
		list.add(getValue("market_price", Float.toString(market_price)));
		list.add(getValue("goods_price", Float.toString(goods_price)));
		list.add(getValue("goods_number", Integer.toString(goods_number)));
		BaseAPI api = new AddCarApi(context, list, param);
		try {
			if (api.doPost()) {
				return (UserCarDatasEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ClassifyDataEntity getClassify(int id) {
		ClassParam param = new ClassParam();
		param.setId(Integer.toString(id));
		BaseAPI api = new ClassApi(context, param);
		try {
			if (api.doGet()) {
				return (ClassifyDataEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public UserCarDataEntity getCarGoodsList(String id, String token) {
		UserInfoParam param = new UserInfoParam();
		param.setUser_id(id);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("token", token));
		BaseAPI api = new CarGoodsListApi(context, list, param);
		try {
			if (api.doPost()) {
				return (UserCarDataEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateCarGoods(String token, int rec_id, String user_id,
			int goods_id, String goods_sn, String goods_name,
			float market_price, float goods_price, int goods_number) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		// param.setUser_id(user_id);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("rec_id", Integer.toString(rec_id)));
		list.add(getValue("user_id", user_id));
		// list.add(getValue("token", token));
		list.add(getValue("goods_id", Integer.toString(goods_id)));
		list.add(getValue("goods_sn", goods_sn));
		list.add(getValue("goods_name", goods_name));
		list.add(getValue("market_price", Float.toString(market_price)));
		list.add(getValue("goods_price", Float.toString(goods_price)));
		list.add(getValue("goods_number", Integer.toString(goods_number)));
		BaseAPI api = new UpdateCarApi(context, list, param);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delCarGoods(String token, String rec_id, String user_id) {
		UserInfoParam infoParam = new UserInfoParam();
		infoParam.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("ids", rec_id));
		list.add(getValue("user_id", user_id));
		BaseAPI api = new DelCarGoodsApi(context, list, infoParam);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserInfoData ownMsg(int user_id, String username, String token) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		param.setUser_name(username);
		param.setUser_id(Integer.toString(user_id));
		BaseAPI api = new OwnMSGApi(context, param);
		try {
			if (api.doGet()) {
				return (UserInfoData) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateUserInfo(String id, String token, String name,
			String sex, String qq, String mobile_phone, String email) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("id", id));
		list.add(getValue("name", name));
		list.add(getValue("sex", sex));
		list.add(getValue("qq", qq));
		list.add(getValue("mobile_phone", mobile_phone));
		list.add(getValue("email", email));
		BaseAPI api = new UpdateUserInfoApi(context, list, param);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AddressDataEntity getAddress(String parent_id) {
		Addressparams pm = new Addressparams();
		pm.setParent_id(parent_id);
		BaseAPI api = new AddressMSGApi(context, pm);
		try {
			if (api.doGet()) {
				return (AddressDataEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addAddress(String token, String consignee, String email,
			int user_id, int country, int province, int city, int district,
			String address, String tel, String mobile) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("consignee", consignee));
		list.add(getValue("email", email));
		list.add(getValue("user_id", Integer.toString(user_id)));
		list.add(getValue("country", Integer.toString(country)));
		list.add(getValue("province", Integer.toString(province)));
		list.add(getValue("city", Integer.toString(city)));
		list.add(getValue("district", Integer.toString(district)));
		list.add(getValue("address", address));
		list.add(getValue("tel", tel));
		list.add(getValue("mobile", mobile));
		BaseAPI api = new AddAddressApi(context, list, param);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyAddressDataEntity getAddressList(String user_id, String token) {
		UserInfoParam param = new UserInfoParam();
		param.setUser_id(user_id);
		param.setToken(token);
		BaseAPI api = new AddressListApi(context, param);
		try {
			if (api.doGet()) {
				return (MyAddressDataEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void deleteAddress(String token, String address_id) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("id", address_id));
		BaseAPI api = new DeleteAddressApi(context, list, param);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AddressInfoEntity addressInfo(String token, String user_id,
			String address_id) {
		UserInfoParam param = new UserInfoParam();
		param.setAddress_id(address_id);
		param.setToken(token);
		param.setUser_id(user_id);
		BaseAPI api = new AddressInfoApi(context, param);
		try {
			if (api.doGet()) {
				return (AddressInfoEntity) api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateAddress(String token, String id, String consignee,
			String email, int user_id, int country, int province, int city,
			int district, String address, String tel, String mobile) {
		UserInfoParam param = new UserInfoParam();
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("id", id));
		list.add(getValue("consignee", consignee));
		list.add(getValue("email", email));
		list.add(getValue("user_id",Integer.toString(user_id)));
		list.add(getValue("country", Integer.toString(country)));
		list.add(getValue("province", Integer.toString(province)));
		list.add(getValue("city", Integer.toString(city)));
		list.add(getValue("district", Integer.toString(district)));
		list.add(getValue("address", address));
		list.add(getValue("tel", tel));
		list.add(getValue("mobile", mobile));
		BaseAPI api = new UpdateAddressApi(context, list, param);
		try {
			if (api.doPost()) {
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void head(String user_id,String token,String f1){
		UserInfoParam param = new UserInfoParam();
		param.setUser_id(user_id);
		param.setToken(token);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(getValue("f1", f1));
		BaseAPI api = new HeadApi(context, list,param);
		try {
			if(api.doUpload()){
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createOrder(String user_id,Address address){
		UserInfoParam param = new UserInfoParam();
		param.setUser_id(user_id);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
//		list.add(getValue("user_id", user_id));
		list.add(getValue("address", new Gson().toJson(address)));
		Log.e("------", new Gson().toJson(address));
		BaseAPI api = new CreateOrderApi(context, list,param);
		try {
			if(api.doPost()){
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getOrderList(String user_id){
		UserInfoParam param = new UserInfoParam();
		param.setUser_id(user_id);
		BaseAPI api = new OrderListApi(context, param);
		try {
			if(api.doGet()){
				api.getHandleResult();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
