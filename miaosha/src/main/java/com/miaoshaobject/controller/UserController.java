package com.miaoshaobject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaobject.controller.viewobject.UserVo;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.response.CommonReturnType;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import javax.lang.model.element.NestingKind;
import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
@Controller("user")
@RequestMapping("/user")
/**
 * CrossOrigin解决跨域问题
 *
 * allowCredentials="true",allowedHeaders="*"
 * DEFAULT_ALLOWED_HEADERS：允许跨域传输所有header参数，将用于使用token放入header域做session共享的跨域请求
 */
@CrossOrigin(allowCredentials = "true", origins ={"*"} )
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户登录接口
     *
     * @param telphone
     * @param password
     * @return
     * @throws BusinssException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "password") String password) throws BusinssException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) ||
                StringUtils.isEmpty(password)) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.EncoderByMD5(password));
        //将登陆凭证加入用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        return CommonReturnType.create(null);
    }

    /**
     * 用户注册接口
     *
     * @param telphone
     * @param otpCode
     * @param gender
     * @param age
     * @param name
     * @param password
     * @return
     * @throws BusinssException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "gender") byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "password") String password) throws BusinssException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpCode是否相符合
        //获取Session中otpCode
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        //判断验证码是否一致
        if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelphone(telphone);
        /* userModel.setGender(new Byte(String.valueOf(gender.intValue())));*/
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncoderByMD5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncoderByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64En = new BASE64Encoder();
        //加密字符串
        String newStr = base64En.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    /**
     * 获取用户OTP短信接口
     *
     * @param telphone
     * @return
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(name = "telphone") String telphone) {

        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        //随机生成[0,99999)的随机数
        int randomInt = random.nextInt(99999);
        //随机数取[10000,109999)
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将OTP验证码与用户的手机号相关联,使用HTTPSession的方式绑定用户手机号与otpCode
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        //将OTP验证码通过短信的方式发送给用户（省略 需要第三方的API）
        System.out.println("telphone=" + telphone + "&otpCode=" + otpCode);
        return CommonReturnType.create(null);
        /* return null;*/
    }

    /**
     * 获取用户的信息
     *
     * @param id
     * @return
     * @throws BusinssException
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinssException {
        //调用service服务器获取id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

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
