package com.zzy.springboot.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求

 */
@Data
public class InterfaceInvokeRequest implements Serializable {

    private static final long serialVersionUID = 2732500904428580814L;
    private Long id;

    private String userRequestParams;



}