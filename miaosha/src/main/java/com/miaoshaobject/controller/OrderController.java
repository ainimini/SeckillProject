package com.miaoshaobject.controller;

import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.response.CommonReturnType;
import com.miaoshaobject.service.OrderService;
import com.miaoshaobject.service.model.OrderModel;
import com.miaoshaobject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/15
 * @Version 1.0
 **/
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**下单请求
     *
     * @param itemId
     * @param amount
     * @return
     * @throws BusinssException
     */
    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount) throws BusinssException {
        //判断用户是否登录
        Boolean islogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (null == islogin || !islogin.booleanValue()) {
            throw new BusinssException(EmBusinssError.USER_NOT_LOGIN, "尚未登录，请登录");
        }
        //获取用户的登录信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, amount);
        return CommonReturnType.create(null);
    }
}
