package com.miaoshaobject.error;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
