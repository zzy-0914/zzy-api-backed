package com.zzy.zzycommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.zzycommon.model.entity.UserInterfaceInfo;

/**
* @author ASUS
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-03-24 13:09:40
*/
public interface InnerUserInterfaceInfoService  {

    /**
     * 用户调用接口统计
     */
    boolean invokeCont(Long interfaceInfoId, Long userId);
    public UserInterfaceInfo getLeftNums(Long interfaceInfoId, Long userId);
}
