package com.miaoshaobject.service;

import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.service.model.UserModel;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10-16:34
 * @Version 1.0
 **/
public interface UserService {
    /**
     * 通过用户Id 获取用户对象的方法
     * @param id
     * @return
     */
    UserModel getUserName(Integer id);

    /**
     * 注册
     * @param userModel
     */
    void register(UserModel userModel) throws BusinssException;

    /**
     * 登录
     * telphone 用户手机号
     * passowrd 加密后的密码
     * @param telphone
     * @param encrptPassowrd
     */
    UserModel validateLogin(String telphone,String encrptPassowrd) throws BusinssException;
}
