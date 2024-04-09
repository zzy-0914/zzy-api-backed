package com.zzy.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.springboot.common.ErrorCode;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.mapper.InterfaceInfoMapper;
import com.zzy.zzycommon.model.entity.InterfaceInfo;
import com.zzy.springboot.service.InterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-03-20 21:43:24
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

    }

}




