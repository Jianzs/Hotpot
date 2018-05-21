package cn.yzh.hotpot.util;

import cn.yzh.hotpot.exception.ConnectWechatException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信工具类
 * 获得openid
 */
@Component
public class WechatUtil {

    private static String WECHAT_OPENID_URL;

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取用户唯一openid
     * @param code  前台给的code
     * @return  openid
     */
    public static String getOpenId(String code)
            throws ConnectWechatException, UnsupportedEncodingException {
        String url = WECHAT_OPENID_URL + URLEncoder.encode(code, "UTF-8");

        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        if (result.getStatusCodeValue() != 200) {
            throw new ConnectWechatException("Connect wechat server failed.");
        }

        JSONObject jsonObject = new JSONObject(result.getBody());

        try {
            return jsonObject.getString("openid");
        } catch (Exception e) {
            return null;
        }
    }

    @Value("${wechat.url}")
    public void setWechatOpenidUrl(String wechatOpenidUrl) {
        WECHAT_OPENID_URL = wechatOpenidUrl;
    }
}