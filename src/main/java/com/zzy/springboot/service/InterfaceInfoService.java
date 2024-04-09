package com.zzy.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.zzycommon.model.entity.InterfaceInfo;

/**
* @author ASUS
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-03-20 21:43:24
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {


    /**
     * 校验
     *
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}
