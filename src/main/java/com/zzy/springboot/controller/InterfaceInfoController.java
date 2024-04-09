package com.zzy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zzyclientsdk.client.ZzyApiClient;
import com.google.gson.Gson;
import com.zzy.springboot.annotation.AuthCheck;
import com.zzy.springboot.common.*;
import com.zzy.springboot.constant.CommonConstant;
import com.zzy.springboot.exception.BusinessException;
import com.zzy.springboot.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.zzy.springboot.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.zzy.springboot.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.zzy.springboot.model.dto.interfaceInfo.InterfaceInvokeRequest;
import com.zzy.springboot.model.enums.InterfaceInfoStatusEnum;
import com.zzy.springboot.service.InterfaceInfoService;
import com.zzy.springboot.service.UserService;
import com.zzy.zzycommon.model.entity.InterfaceInfo;
import com.zzy.zzycommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private ZzyApiClient zzyApiClient;
    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // content 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "content", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }


    /**
     * 上线接口
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfoByPage(@RequestBody IdRequest idRequest, HttpServletRequest request) {
         if (idRequest == null || idRequest.getId() <=0){
             throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
         }
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
        }
        //判断该接口是否可以调用
        com.example.zzyclientsdk.model.User user = new com.example.zzyclientsdk.model.User();
        user.setUsername("test");
        String userNameByPost = zzyApiClient.getUserNameByPost(user);
        if ( StringUtils.isBlank(userNameByPost)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"验证失败");
        }
        InterfaceInfo newInterfaceInfo = new InterfaceInfo();
        newInterfaceInfo.setId(id);
        newInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(newInterfaceInfo);

        return ResultUtils.success(result);
    }
    /**
     * 上线接口
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfoByPage(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <=0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
        }
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
        }
        InterfaceInfo newInterfaceInfo = new InterfaceInfo();
        newInterfaceInfo.setId(id);
        newInterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(newInterfaceInfo);

        return ResultUtils.success(result);
    }
    /**
     * 测试调用
     */
    @PostMapping(value = "/invoke",produces = "application/json;charset=UTF-8")
    public BaseResponse<Object> invokeInterfaceInfoByPage(@RequestBody InterfaceInvokeRequest interfaceInvokeRequest, HttpServletRequest request) throws Exception {
        if (interfaceInvokeRequest == null || interfaceInvokeRequest.getId() <=0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
        }
        Long id = interfaceInvokeRequest.getId();
        String userRequestParams = interfaceInvokeRequest.getUserRequestParams();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求数据不存在");
        }
        if (interfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口关闭");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        ZzyApiClient zzyApiClient = new ZzyApiClient(accessKey,secretKey);
        // Gson gson = new Gson();
        // com.example.zzyclientsdk.model.User user =
        //         gson.fromJson(userRequestParams, com.example.zzyclientsdk.model.User.class);
        //通过反射指定需要调用的接口
        String methodName = interfaceInfo.getName();
        Method[] methods = zzyApiClient.getClass().getMethods();
        for (Method method_ : methods) {
            if(method_.getName().equals(methodName)){
                Class<?>[] types = method_.getParameterTypes();
                if (types.length!=0){
                    Method method = null;
                    try {
                        method = zzyApiClient.getClass().getMethod(methodName, types[0]);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    Object result = sent(ZzyApiClient.class, method, userRequestParams, types[0], accessKey, secretKey);
                    return ResultUtils.success(result);
                }else {//空函数
                    Method method = zzyApiClient.getClass().getMethod(methodName);
                    Object result = sentNullFunction(ZzyApiClient.class, method, accessKey, secretKey);
                    return ResultUtils.success(result);
                }

            }
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }

    private Object sent(Class<?> clazz, Method method, String jsonStr, Class<?> parameterClazz, String accessKey, String secretKey) throws Exception {

        Constructor constructor = clazz.getDeclaredConstructor(String.class, String.class);

        //通过反射创建clazz的实例,构造器
        ZzyApiClient yuApiClient = (ZzyApiClient) constructor.newInstance(accessKey, secretKey);
        Object parameter = null;
        // 如果jsonStr不为空，则尝试将其转换为对象
        if (jsonStr != null && !jsonStr.isEmpty()) {
            // 将json字符串转成对象
            Gson gson = new Gson();
            parameter = gson.fromJson(jsonStr, parameterClazz);
        } else {
            // 如果jsonStr为空，则根据参数类型使用默认构造函数创建一个空的对象
            parameter = parameterClazz.newInstance();
        }
        //返回执行方法的结果
//        method.invoke(yuApiClient,parameter);
        return method.invoke(yuApiClient,parameter);

    }
    private Object sentNullFunction(Class<?> clazz, Method method,  String accessKey, String secretKey) throws Exception {

        Constructor constructor = clazz.getDeclaredConstructor(String.class, String.class);

        //通过反射创建clazz的实例,构造器
        ZzyApiClient apiClient = (ZzyApiClient) constructor.newInstance(accessKey, secretKey);

        //返回执行方法的结果
//        method.invoke(yuApiClient,parameter);
        return method.invoke(apiClient);

    }
}