package com.zzy.zzyapiinterface.controller;


import com.example.zzyclientsdk.Utils.SignUtils;
import com.example.zzyclientsdk.model.Constellation;
import com.example.zzyclientsdk.model.User;
import com.zzy.zzyapiinterface.service.MyApiInterfaceService;
import com.zzy.zzyapiinterface.service.impl.MyApiInterfaceServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    MyApiInterfaceService myApiInterfaceService = new MyApiInterfaceServiceImpl();
    @GetMapping("/")
    public String getNameByGet(@RequestParam(required = false) String name,HttpServletRequest request){
        System.out.println(request.getHeader("zzy"));
        return "GET 你的名字是"+name;
    }
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String body = request.getHeader("body");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");

        if (Long.parseLong(nonce)>10000){
            throw new RuntimeException("无权限");
        }
        //时间和当前时间不超过 5分钟
        if (Math.abs(Long.parseLong(timestamp)-new Date().getTime()/1000)>300){
            throw new RuntimeException("shijian无权限");
        }
        String serverSign= SignUtils.genSign(body,"java");
        // if (!sign.equals(serverSign)){
        //     throw new RuntimeException("无权限");
        // }

        return "POST REST 你的名字是"+user.getUsername();
    }

    @GetMapping("/sexWord")
    public String getSexWord(){
        return myApiInterfaceService.getSexWord();
    }

    @GetMapping("/itNews")
    public String getItNews(){
        return myApiInterfaceService.getItNews();
    }
    @GetMapping("/constellation")
    public String getConstellation(@RequestBody Constellation constellation){
        String time = constellation.getTime();
        String type = constellation.getType();
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("type",type);
        stringStringHashMap.put("time",time);
        return myApiInterfaceService.getConstellation(stringStringHashMap);
    }

}
