package com.miaoshaobject.error;

public enum EmBusinssError implements CommonError {
    //通用错误类型100001
    PATAMETER_VALIDATION_ERROR(100001,"参数不合法"),
    UNKNOWN_ERROR(100002,"未知错误"),
    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(200001, "用户不存在"),
    USER_LOGIN_FAIL(200002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(200003, "尚未登录，请登录"),
    //30000开头为交易形错误定义
    STOCK_NOT_ENOUGH(300001,"库存不足"),

    ;

    private EmBusinssError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int errCode;
    public String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
