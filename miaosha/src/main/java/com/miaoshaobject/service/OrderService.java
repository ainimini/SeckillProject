package com.miaoshaobject.service;

import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.service.model.OrderModel;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/15-15:51
 * @Version 1.0
 **/
public interface OrderService {
    /**
     * 商品订单
     * @param userId
     * @param orderId
     * @param amount
     * @return
     */
    OrderModel createOrder(Integer userId, Integer orderId, Integer amount) throws BusinssException;
}
