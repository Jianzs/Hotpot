package cn.yzh.hotpot.util;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class SendMessageUtil {
    private static String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";
    private static String GET_ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa168ae374a9bfc84&secret=84297bd637950a40216a6ac766fe7e54";

    public static void send(String message) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);

        String accessToken = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(String.format(SEND_MESSAGE_URL, accessToken), httpEntity, String.class);
        System.out.println(res);
    }

    private static String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(GET_ACCESS_URL, String.class);
        JSONObject jsonObject = new JSONObject(res);
        return jsonObject.getString("access_token");
    }
}
