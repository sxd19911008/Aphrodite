package com.aphrodite.insurance.utils;

import com.aphrodite.common.api.ApiResponse;
import com.eredar.janus.core.aspect.JanusAspectSupport;
import com.eredar.janus.core.constants.JanusConstants;

/**
 * 响应对象相关工具方法
 */
public class ResponseUtils {

    /**
     * 接口运行成功时，根据 Janus 分流结果返回不同的message
     *
     * @param methodId 根据哪个方法的分流结果返回message，则填入该方法的methodId
     * @param data 接口返回的数据
     * @return 组装好的 ApiResponse 对象
     */
    public static  <T> ApiResponse<T> success(String methodId, T data) {
        String masterBranchName = JanusAspectSupport.getMasterBranchName(methodId);
        if (JanusConstants.SECONDARY.equals(masterBranchName)) {
            return ApiResponse.success("PL/SQL运行成功", data);
        } else {
            return ApiResponse.success(data);
        }
    }
}
