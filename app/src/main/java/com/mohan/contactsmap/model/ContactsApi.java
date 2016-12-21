package com.mohan.contactsmap.model;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;




public class ContactsApi extends Request<JSONArray>
{
	
	private Response.Listener<JSONArray> listener;

	public ContactsApi(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener)
	{
		super(Method.GET, getApiUrl(), errorListener);
		this.listener = listener;
	}

	static String getApiUrl() {
		
		return "http://private-b08d8d-nikitest.apiary-mock.com/contacts";
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response)
	{

		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			JSONArray appObj=new JSONArray(jsonString);
			
			return Response.success(appObj, HttpHeaderParser.parseCacheHeaders(response));
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			return Response.error(new ParseError(e));
		} 
		catch (JSONException je) 
		{
			je.printStackTrace();
			return Response.error(new ParseError(je));
		}
	}
	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {

		return volleyError;
	}
	@Override
	protected void deliverResponse(JSONArray response) {
		
		listener.onResponse(response);
	}
	
}
