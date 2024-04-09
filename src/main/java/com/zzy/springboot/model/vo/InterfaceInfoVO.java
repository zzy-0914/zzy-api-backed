package com.zzy.springboot.model.vo;

import com.zzy.zzycommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {
    private static final long serialVersionUID = 6480428514105861525L;
    private Integer totalNum;

}
