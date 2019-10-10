package com.miaoshaobject.service.impl;

import com.miaoshaobject.dao.UserInfoMapper;
import com.miaoshaobject.dao.UserPasswordMapper;
import com.miaoshaobject.dataobject.UserInfo;
import com.miaoshaobject.dataobject.UserPassword;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserPasswordMapper userPasswordMapper;
    @Override
    public UserModel getUserName(Integer id) {
        //调用userInfoMapper获取对应的用户dataobject
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (null == userInfo) {
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPassword userPassword = userPasswordMapper.selectByUserId(userInfo.getId());
        return convertFromDataObject(userInfo,userPassword);
    }

    public UserModel convertFromDataObject(UserInfo userInfo, UserPassword userPassword){
        if (null == userInfo) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo,userModel);
        if (null !=userPassword ) {
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }
}
