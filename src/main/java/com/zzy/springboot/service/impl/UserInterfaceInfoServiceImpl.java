package com.zzy.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.springboot.common.ErrorCode;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.mapper.UserInterfaceInfoMapper;
import com.zzy.springboot.service.UserInterfaceInfoService;
import com.zzy.zzycommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-03-24 13:09:40
*/
@Service
public class UserInterfaceInfoServiceImpl
        extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {


    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo
            , boolean add) {
        if (userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );
        }
        if (add){
            if (userInterfaceInfo.getInterfaceInfoId()<=0
                    ||userInterfaceInfo.getUserId()<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR );
            }
        }
        if (userInterfaceInfo.getLeftNum()<0){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"次数不够");
        }
    }

    @Override
    public boolean invokeCount(Long interfaceInfoId, Long userId) {
        if (interfaceInfoId<=0 || userId <= 0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口或用户不存在");
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("leftNum = leftNum -1,totalNum = totalNum +1");
        boolean update = this.update(updateWrapper);
        return update;
    }
    @Override
    public UserInterfaceInfo getLeftNums(Long interfaceInfoId, Long userId){
        if (interfaceInfoId<=0 || userId <= 0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口或用户不存在");
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        queryWrapper.eq("userId",userId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        return userInterfaceInfo;
    }
}




