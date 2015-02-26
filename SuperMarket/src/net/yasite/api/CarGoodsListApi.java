package net.yasite.api;

import java.util.List;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;
import net.yasite.entity.UserCarDataEntity;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class CarGoodsListApi extends BaseAPI {

	public CarGoodsListApi(Context context, List<NameValuePair> pm,
			UserInfoParam params) {
		super(context, pm, params);
		setMethod("http://www.yasite.net/shopapi/index.php/cartController/getGoodList/"+params.getUser_id());
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserCarDataEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), UserCarDataEntity.class);
	}

}
