package com.zzy.zzycommon.service;


import com.zzy.zzycommon.model.entity.User;


/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService  {
    /**
     * 查看是否分配给用户accessKey，secretKey
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);


}
