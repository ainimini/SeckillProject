package com.miaoshaobject.service.model;

import jdk.nashorn.internal.ir.debug.PrintVisitor;

import java.math.BigDecimal;

/**
 * @ClassName 订单的交易模型
 * @Description TOOD
 * @Author X
 * @Data 2019/10/15
 * @Version 1.0
 **/
public class OrderModel {

    /**
     * 订单id
     */
    private String id;
    /**
     * 下单的用户id
     */
    private Integer userId;
    /**
     * 下单的商品id
     */
    private Integer itemId;
    /**
     * 购买商品的单价
     */
    private BigDecimal itemPrice;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 购买金额
     */
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
