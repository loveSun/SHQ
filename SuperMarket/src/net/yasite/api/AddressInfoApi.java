package net.yasite.api;

import java.util.List;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.AddressDataEntity;
import net.yasite.entity.AddressInfoEntity;
import net.yasite.entity.MyAddressDataEntity;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class AddressInfoApi extends BaseAPI {

//	public AddressInfoApi(Context context, List<NameValuePair> pm,
//			UserInfoParam params) {
//		super(context, pm, params);
//		// TODO Auto-generated constructor stub
//		setMethod("http://www.yasite.net/shopapi/index.php/userController/getAddressInfo/"+params.getUser_id()+"/"+params.getAddress_id()+"/"+params.getToken());
//	}

	public AddressInfoApi(Context context, UserInfoParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/userController/getAddressInfo/"+pm.getUser_id()+"/"+pm.getAddress_id()+"/"+pm.getToken());
	}

	@Override
	public AddressInfoEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), AddressInfoEntity.class);
	}
}
