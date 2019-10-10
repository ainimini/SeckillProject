package com.miaoshaobject.service;

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
}
