package com.zzy.springboot.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.springboot.common.ErrorCode;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.mapper.UserMapper;
import com.zzy.zzycommon.model.entity.User;
import com.zzy.zzycommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey",accessKey);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }
}
