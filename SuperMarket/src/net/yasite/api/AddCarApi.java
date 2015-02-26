package net.yasite.api;

import java.util.List;

import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.UserCarDataEntity;
import net.yasite.entity.UserCarDatasEntity;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class AddCarApi extends BaseAPI {

	public AddCarApi(Context context, List<NameValuePair> pm,
			UserInfoParam params) {
		super(context, pm, params);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/cartController/addGood/"+params.getToken());
	}

	@Override
	public UserCarDatasEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), UserCarDatasEntity.class);
	}

}
