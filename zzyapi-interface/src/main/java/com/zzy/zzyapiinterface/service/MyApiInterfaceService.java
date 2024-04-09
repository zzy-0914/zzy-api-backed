package com.zzy.zzyapiinterface.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface MyApiInterfaceService {
    public String getSexWord();
    public String getItNews();
    public String getConstellation(Map<String,Object> params);
}
