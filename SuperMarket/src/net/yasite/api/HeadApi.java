package net.yasite.api;

import java.util.List;

import javax.crypto.spec.OAEPParameterSpec;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class HeadApi extends BaseAPI {

	

	public HeadApi(Context context, List<NameValuePair> pm, UserInfoParam params) {
		super(context, pm, params);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/userController/updateUserHeadImg/"+params.getUser_id()+"/"+params.getToken());
	}
	@Override
	public Object handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
