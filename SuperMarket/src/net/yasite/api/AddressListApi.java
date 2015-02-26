package net.yasite.api;

import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.MyAddressDataEntity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class AddressListApi extends BaseAPI {

	public AddressListApi(Context context, UserInfoParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/userController/getUserAddressList/"+pm.getUser_id()+"/"+pm.getToken());
	}

	@Override
	public MyAddressDataEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), MyAddressDataEntity.class);
	}
}
