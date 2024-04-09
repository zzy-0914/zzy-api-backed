package com.zzy.zzycommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.zzycommon.model.entity.InterfaceInfo;

/**
* @author ASUS
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-03-20 21:43:24
*/
public interface InnerInterfaceInfoService  {
    /**
     * 从数据库中查询接口是否存在
     */
    InterfaceInfo getInterfaceInfo(String url,String method);

}
