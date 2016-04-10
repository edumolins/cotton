
package com.cotton.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class ConnectManager {
	

	public static String callMethod(){
		String result = "";
		String urlJSON = "";		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		HttpResponse response;		
		try {
			httppost = new HttpPost("http://devel.liquid.cat/cockpit/rest/api/collections/get/Venue");
			List <BasicNameValuePair>valuePairs = new ArrayList<BasicNameValuePair>();  
		    valuePairs.add(new BasicNameValuePair("token", "3f2e4060cd40d6e2a72b08ca"));
		    httppost.setEntity(new UrlEncodedFormEntity(valuePairs,"UTF-8"));
		    response = httpclient.execute(httppost);
	        result = EntityUtils.toString(response.getEntity());
	        //JSON Conversion
	        //JSONObject jsonObject = new JSONObject(result);
	        //Status KO
			//result = jsonObject.get("data").toString();
		}catch(Exception e){			
			//Exception, Error 500, not connection, etc
			//result = Constants.PLEASE_ERROR_EXCEPTION;
		}   
		return result;
	}
}