package com.miaoshaobject.error;

/**
 * @ClassName 包装器业务异常类实现
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
public class BusinssException extends Exception implements CommonError {
    private CommonError commonError;

    /**
     * 直接接受BusinssException的传参用于构造业务的异常
     *
     * @param commonError
     */
    public BusinssException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public BusinssException(CommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
