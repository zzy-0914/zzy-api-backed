package com.zzy.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author yupi
 */
@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invoke(){
        boolean invoke = userInterfaceInfoService.invokeCount(1L, 1L);
        System.out.println(invoke);
    }
    @Test
    void  test(){
        String s = "  hello world  ";
        String s1 = reverseWords(s);
        System.out.println(s1);
    }

    public String reverseWords(String s) {
        String[] vars =  s.split(" ");
        System.out.println(vars);
        int low  = 0;
        int heigh = vars.length-1;
        while(low < heigh){
            String temp = vars[low];
            vars[low] = vars[heigh];
            vars[heigh] = temp;
            low++;
            heigh--;
        }
        String str = "";
        for(int i = 0; i <vars.length;i++ ){
            if(i ==vars.length-1){
                str = str+vars[i];
            }else{
                str = str+vars[i]+" ";
            }
        }
        return str;
    }
}