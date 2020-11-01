package api.common;


import com.tangym.auth.AbstractLogin;
import com.tangym.utils.RequestUtil;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 为了可以实例化此类，必须实现全部的抽象方法
 */
public class ZentaoLogin extends AbstractLogin {
    RequestUtil requestUtil = new RequestUtil();

    @Override
    public Headers getLoginHeaders(String url, String account, String password) {
        return null;
    }

    @Override
    public Cookies getLoginCookies(String url, String account, String password) {
        // 1、构造请求体
        Map<String, Object> loginBody = new HashMap<>();
        loginBody.put("account", account);
        loginBody.put("password", password);
        // 2、发送post请求
        Response response = requestUtil.httpPost(url, loginBody);
        // 3、返回请求cookies
        return response.getDetailedCookies();
    }

    @Override
    public String getLoginToken(String url, String account, String password) {
        return null;
    }


}
