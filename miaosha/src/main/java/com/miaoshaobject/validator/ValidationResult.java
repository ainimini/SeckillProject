package com.miaoshaobject.validator;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/13
 * @Version 1.0
 **/
public class ValidationResult {
    /**
     * 校验结果是否有错
     */
    private boolean hasErrors = false;

    /**
     * 存放错误信息的Map
     */
    private Map<String, String> errorMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 实现通用的 通过格式化字符串信息获取 所有错误结果的Message方法
     * @return
     */
    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }

}
