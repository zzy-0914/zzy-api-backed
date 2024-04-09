package com.example.zzyclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.zzyclientsdk.Utils.SignUtils;
import com.example.zzyclientsdk.model.Constellation;
import com.example.zzyclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口客户端
 */

public class ZzyApiClient {
    private static final  String GATEWAY_HOST = "http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public ZzyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getUserNameByPost(User user){
        String json= JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post(GATEWAY_HOST+"/api/user/user/")
                .addHeaders(getHeaderMap(json)).body(json).execute();
        System.out.println(execute.getStatus());
        String result=execute.body();
        System.out.println(result);
        return result;
    }
    public String getSexWord(){
        HttpResponse execute = HttpRequest.get(GATEWAY_HOST + "/api/user/sexWord/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        return execute.body();
    }
    public String getItNews(){
        HttpResponse execute = HttpRequest.get(GATEWAY_HOST + "/api/user/itNews/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        return execute.body();
    }
    public String getConstellation(Constellation constellation){
        String str = JSONUtil.toJsonStr(constellation);
        HttpResponse execute = HttpRequest.get(GATEWAY_HOST + "/api/user/constellation/")
                .addHeaders(getHeaderMap(str))
                .body(str)
                .execute();
        return execute.body();
    }


    private Map<String,String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", SignUtils.genSign(body,secretKey));
        return hashMap;
    }



}
