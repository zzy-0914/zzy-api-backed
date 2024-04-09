package com.zzy.springboot.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.springboot.common.ErrorCode;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.mapper.InterfaceInfoMapper;
import com.zzy.zzycommon.model.entity.InterfaceInfo;
import com.zzy.zzycommon.model.entity.User;
import com.zzy.zzycommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QueryWrapper<InterfaceInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("url",url);
        infoQueryWrapper.eq("method",method);
        return interfaceInfoMapper.selectOne(infoQueryWrapper);

    }
}
