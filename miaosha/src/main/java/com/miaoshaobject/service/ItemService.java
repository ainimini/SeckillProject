package com.miaoshaobject.service;

import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.service.model.ItemModel;

import java.util.List;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/13
 * @Version 1.0
 **/
public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     * @throws BusinssException
     */
    ItemModel createItem(ItemModel itemModel) throws BusinssException;

    /**
     * 商品列表浏览
     *
     * @return
     */
    List<ItemModel> listModel();

    /**
     * 商品详情浏览
     *
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);

    /**
     * 库存扣减
     * @param itemId
     * @param amount
     * @return
     * @throws BusinssException
     */
    boolean decreaseStock(Integer itemId,Integer amount)throws BusinssException;
}
