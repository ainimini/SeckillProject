package com.miaoshaobject.controller;

import com.miaoshaobject.controller.viewobject.ItemVo;
import com.miaoshaobject.dataobject.Item;
import com.miaoshaobject.error.BusinssException;
import com.miaoshaobject.response.CommonReturnType;
import com.miaoshaobject.service.ItemService;
import com.miaoshaobject.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/13
 * @Version 1.0
 **/
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    /**
     * 创建商品
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinssException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinssException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVo itemVo = convertVoFromItemModel(itemModelForReturn);
        return CommonReturnType.create(itemVo);
    }

    private ItemVo convertVoFromItemModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        return itemVo;
    }
}
