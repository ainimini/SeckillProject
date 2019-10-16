package com.miaoshaobject.service.impl;

import com.miaoshaobject.dao.OrderInfoMapper;
import com.miaoshaobject.dao.SequenceInfoMapper;
import com.miaoshaobject.dataobject.OrderInfo;
import com.miaoshaobject.dataobject.SequenceInfo;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.service.ItemService;
import com.miaoshaobject.service.OrderService;
import com.miaoshaobject.service.UserService;
import com.miaoshaobject.service.model.ItemModel;
import com.miaoshaobject.service.model.OrderModel;
import com.miaoshaobject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/15
 * @Version 1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinssException {
        //校验下单的状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (null == itemModel) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, "该商品不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (null == userModel) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, "尚未登录，请登录");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, "数量信息不正确");
        }
        //落单减库存   （减库存的方法有两种：1.落单减库存 2.支付减库存）
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinssException(EmBusinssError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号(订单号)
        orderModel.setId(generateOrderNo());
        OrderInfo orderInfo = converFromOrderModel(orderModel);
        orderInfoMapper.insertSelective(orderInfo);
        //返回前端
        return orderModel;
    }

    /* public static void main(String[] args) {
         LocalDateTime now = LocalDateTime.now();

         System.out.println( now.format(DateTimeFormatter.ISO_DATE).replace("-",""));
     }*/
    @Transactional(propagation = Propagation.REQUIRES_NEW)//不与创建订单在一个事务
    private String generateOrderNo(){
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息 年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        //中间6位为自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceInfo sequenceInfo = sequenceInfoMapper.getSequenceByName("order_info");
        sequence = sequenceInfo.getCurrentValue();
        sequenceInfo.setCurrentValue(sequenceInfo.getCurrentValue() + sequenceInfo.getStep());
        sequenceInfoMapper.updateByPrimaryKeySelective(sequenceInfo);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //最后2位为分库分表位(暂时写死)
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderInfo converFromOrderModel(OrderModel orderModel) {
        if (null == orderModel) {
            return null;
        }
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderModel, orderInfo);
        orderInfo.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderInfo.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderInfo;
    }
}
