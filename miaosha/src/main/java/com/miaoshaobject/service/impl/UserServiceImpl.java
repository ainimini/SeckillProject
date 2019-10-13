package com.miaoshaobject.service.impl;

/*import com.alibaba.druid.util.StringUtils;
import com.miaoshaobject.dao.UserInfoMapper;
import com.miaoshaobject.dao.UserPasswordMapper;
import com.miaoshaobject.dataobject.UserInfo;
import com.miaoshaobject.dataobject.UserPassword;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;*/

import com.alibaba.druid.util.StringUtils;
import com.miaoshaobject.dao.UserInfoMapper;
import com.miaoshaobject.dao.UserPasswordMapper;
import com.miaoshaobject.dataobject.UserInfo;
import com.miaoshaobject.dataobject.UserPassword;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.UserModel;
import com.miaoshaobject.validator.ValidationResult;
import com.miaoshaobject.validator.ValidatorImpl;
import com.sun.java.swing.plaf.motif.MotifMenuBarUI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Autowired
    private ValidatorImpl validator;

    /**
     * 通过用户Id 获取用户对象的方法
     *
     * @param id
     * @return
     */
    @Override
    public UserModel getUserName(Integer id) {
        //调用userInfoMapper获取对应的用户dataobject
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (null == userInfo) {
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPassword userPassword = userPasswordMapper.selectByUserId(userInfo.getId());
        return convertFromDataObject(userInfo, userPassword);
    }

    /**
     * 实现登录方法
     *
     * @param telphone
     * @param encrptPassowrd
     */
    @Override
    public UserModel validateLogin(String telphone, String encrptPassowrd) throws BusinssException {
        //通过用户手机获取用户信息
        UserInfo userInfo = userInfoMapper.selectByTelphone(telphone);
        if (null == userInfo) {
            throw new BusinssException(EmBusinssError.USER_LOGIN_FAIL);
        }
        UserPassword userPassword = userPasswordMapper.selectByUserId(userInfo.getId());
        UserModel userModel = convertFromDataObject(userInfo, userPassword);
        //对比用户信息加密的密码是否和传输进来的密码相匹配
        if (!StringUtils.equals(encrptPassowrd,userModel.getEncrptPassword())){
            throw new BusinssException(EmBusinssError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    /**
     * 实现注册方法
     *
     * @param userModel
     * @throws BusinssException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserModel userModel) throws BusinssException {
        if (null == userModel) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR);
        }
       /* if (StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR);
        }*/
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //实现model转化为dataobject方法
        UserInfo userInfo = convertFromModel(userModel);
        try {
            userInfoMapper.insertSelective(userInfo);
        } catch (DuplicateKeyException ex) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }

        userModel.setId(userInfo.getId());
        UserPassword userPassword = convertPasswordFromModel(userModel);
        userPasswordMapper.insertSelective(userPassword);

        return;
    }

    private UserPassword convertPasswordFromModel(UserModel userModel) {
        if (null == userModel) {
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        userPassword.setUserId(userModel.getId());
        return userPassword;
    }

    private UserInfo convertFromModel(UserModel userModel) {
        if (null == userModel) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userModel, userInfo);
        return userInfo;
    }

    private UserModel convertFromDataObject(UserInfo userInfo, UserPassword userPassword) {
        if (null == userInfo) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);
        if (null != userPassword) {
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }
}
