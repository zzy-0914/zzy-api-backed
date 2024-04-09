package com.zzy.zzyapiinterface.service.impl;

import cn.hutool.http.HttpUtil;
import com.zzy.zzyapiinterface.service.MyApiInterfaceService;

import java.util.Map;

public class MyApiInterfaceServiceImpl implements MyApiInterfaceService {
    @Override
    public String getSexWord() {
        String result = HttpUtil.get("https://api.vvhan.com/api/text/sexy");
        return result;
    }

    @Override
    public String getItNews() {
        String result = HttpUtil.get("https://api.vvhan.com/api/hotlist/itNews");
        return result;
    }

    @Override
    public String getConstellation(Map<String, Object> params) {
        String result = HttpUtil.get("https://api.vvhan.com/api/horoscope",params);
        return result;
    }

}
