package net.yasite.api;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.UserInfoData;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class OwnMSGApi extends BaseAPI {

	public OwnMSGApi(Context context, UserInfoParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/userController/getUserInfo/"+pm.getUser_id()+"/"+pm.getUser_name()+"/"+pm.getToken());
	}

	@Override
	public UserInfoData handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), UserInfoData.class);
	}

}
