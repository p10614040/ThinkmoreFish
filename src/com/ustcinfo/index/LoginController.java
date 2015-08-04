package com.ustcinfo.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.jfinal.core.Controller;

public class LoginController extends Controller {
	
	Logger log = Logger.getLogger(LoginController.class);
	
	String LOGIN_URL = "http://60.174.249.206:48080/bwp/j_spring_security_check";
	
	public void login() {
		String truePassWord = getPara("f_password");
		String jUserName = getPara("j_username");
		String jPassWord = getPara("j_password");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("j_username", jUserName);
		map.put("j_password", jPassWord);
		Connection conn = Jsoup.connect(LOGIN_URL);
		try {
			Response response = conn.ignoreContentType(true).method(Method.POST).data(map).timeout(20000).execute();
			System.out.println(response.body());
			JSONObject bodyObj;
			try {
				bodyObj = new JSONObject(response.body());
				StringBuffer loginInfo = new StringBuffer();
				loginInfo.append("登录用户：" + jUserName + "；登录密码：" + truePassWord + "；");
				if("true".equals(bodyObj.getString("success"))){
					loginInfo.append("》》登录成功！");
				}else{
					loginInfo.append("》》登录失败！");
				}
				log.info(loginInfo.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			this.renderJson(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


