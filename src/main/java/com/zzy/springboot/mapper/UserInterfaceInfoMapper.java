package com.zzy.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.zzycommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author ASUS
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-03-24 13:09:40
* @Entity com.zzy.springboot.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
        List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




