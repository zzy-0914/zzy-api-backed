package com.zzy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.springboot.annotation.AuthCheck;
import com.zzy.springboot.common.BaseResponse;
import com.zzy.springboot.common.ErrorCode;
import com.zzy.springboot.common.ResultUtils;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.mapper.UserInterfaceInfoMapper;
import com.zzy.springboot.model.vo.InterfaceInfoVO;
import com.zzy.springboot.service.InterfaceInfoService;
import com.zzy.zzycommon.model.entity.InterfaceInfo;
import com.zzy.zzycommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listInvokeInterface(){
        List<UserInterfaceInfo> userInterfaceInfoList =
                userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        //获取user InterfaceInfo 关系表中的数据  interfaceInfoId :UserInterfaceInfo
        Map<Long, List<UserInterfaceInfo>> interfaceInfoMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.in("id",interfaceInfoMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(infoQueryWrapper);

        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOS = list.stream().map(sb -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(sb, interfaceInfoVO);
            Integer totalNum = interfaceInfoMap.get(sb.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
    return ResultUtils.success(interfaceInfoVOS);
    }
}
