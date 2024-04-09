package com.zzy.springboot.service.impl.inner;

import com.zzy.springboot.service.UserInterfaceInfoService;
import com.zzy.zzycommon.model.entity.UserInterfaceInfo;
import com.zzy.zzycommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCont(Long interfaceInfoId, Long userId) {
        return  userInterfaceInfoService.invokeCount(interfaceInfoId,userId);

    }
    @Override
    public UserInterfaceInfo getLeftNums(Long interfaceInfoId, Long userId){
        return  userInterfaceInfoService.getLeftNums(interfaceInfoId,userId);
    }
}
