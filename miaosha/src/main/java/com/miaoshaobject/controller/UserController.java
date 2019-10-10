package com.miaoshaobject.controller;

import com.miaoshaobject.controller.viewobject.UserVo;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.response.CommonReturnType;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;


/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    //获取用户Otp短信接口
    @RequestMapping("/getOtp")
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(name = "telphone") String telphone){

        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        //随机生成[0,99999)的随机数
        int randomInt = random.nextInt(99999);
        //随机数取[10000,109999)
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将OTP验证码与用户的手机号相关联,使用HTTPSession的方式绑定用户手机号与otpCode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信的方式发送给用户（省略 需要第三方的API）
        System.out.println("telphone="+ telphone+"&otpCode="+otpCode);
        return CommonReturnType.create(null);
       /* return null;*/
    }

    /**
     * 获取用户的信息
     * @param id
     * @return
     * @throws BusinssException
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinssException {
        //调用service服务器获取id的用户对象并返回给前端
        UserModel userModel = userService.getUserName(id);

        //若获取的用户的信息不存在
        if (null == userModel) {
            throw new BusinssException(EmBusinssError.USER_NOT_EXIST);
        }

        //将核心领域模型对象用户转化为可提供的UI使用的viewobject
        UserVo userVo = converFromModel(userModel);
        return CommonReturnType.create(userVo);
    }

    private UserVo converFromModel(UserModel userModel) {
        if (null == userModel) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }
}
