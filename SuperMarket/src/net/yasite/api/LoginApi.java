package net.yasite.api;

import java.util.List;

import net.yasite.entity.UserInfoData;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class LoginApi extends BaseAPI {

	public LoginApi(Context context, List<NameValuePair> pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/userController/login");
	}

	@Override
	public UserInfoData handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), UserInfoData.class);
	}

}
