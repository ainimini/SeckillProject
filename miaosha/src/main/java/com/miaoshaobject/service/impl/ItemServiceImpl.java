package com.miaoshaobject.service.impl;

import com.miaoshaobject.dao.ItemMapper;
import com.miaoshaobject.dao.ItemStockMapper;
import com.miaoshaobject.dataobject.Item;
import com.miaoshaobject.dataobject.ItemStock;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.error.EmBusinssError;
import com.miaoshaobject.service.ItemService;
import com.miaoshaobject.service.model.ItemModel;
import com.miaoshaobject.validator.ValidationResult;
import com.miaoshaobject.validator.ValidatorImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.PrintConversionEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/13
 * @Version 1.0
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemStockMapper itemStockMapper;


    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinssException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinssException(EmBusinssError.PATAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //将itemModel转化为dataobject
        Item item = this.converItemFromItemModel(itemModel);

        //写入数据库
        itemMapper.insertSelective(item);
        itemModel.setId(item.getId());
        ItemStock itemStock = this.converItemStockFromItemModel(itemModel);
        itemStockMapper.insertSelective(itemStock);
        //返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listModel() {

        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (null == item) {
            return null;
        }
        //操作获得库存数量
        ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());

        //将dataobjec转化为model
        ItemModel itemModel = converModelDataObject(item,itemStock);
        return itemModel;
    }


    private Item converItemFromItemModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemModel, item);
        item.setPrice(itemModel.getPrice().doubleValue());
        return item;
    }

    private ItemStock converItemStockFromItemModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemStock itemStock = new ItemStock();
        itemStock.setItemId(itemModel.getId());
        itemModel.setStock(itemStock.getStock());
        return itemStock;
    }

    private ItemModel converModelDataObject(Item item,ItemStock itemStock){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStock.getStock());
        return itemModel;
    }
}
