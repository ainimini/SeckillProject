package com.miaoshaobject.controller;

import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName 使用@ExceptionHandler注解解决未被controller层吸收的exception
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
public class BaseController {

public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinssException) {
            BusinssException businssException = (BusinssException) ex;
            responseData.put("errCode", businssException.getErrCode());
            responseData.put("errMsg", businssException.getErrMsg());
        } else {
            responseData.put("errCode", EmBusinssError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinssError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
