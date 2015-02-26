package net.yasite.api;

import net.yasite.api.params.Addressparams;
import net.yasite.api.params.BaseHttpParam;
import net.yasite.entity.AddressDataEntity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class AddressMSGApi extends BaseAPI {

	public AddressMSGApi(Context context, Addressparams pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/regionController/getRegion/"+pm.getParent_id());
	}

	@Override
	public AddressDataEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), AddressDataEntity.class);
	}

}
