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

import javax.management.relation.RelationNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
     *
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

    /**
     * 商品详情页浏览
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getItem", method = {RequestMethod.GET})
    @ResponseBody

    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVo itemVo = convertVoFromItemModel(itemModel);
        return CommonReturnType.create(itemVo);
    }

    /**
     * 商品列表页浏览
     * @return
     */
    @RequestMapping(value = "/listItem", method = {RequestMethod.GET})
    @ResponseBody

    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listModel();

        //使用stream api将list内的itemModel转化为itemVo
        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromItemModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
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
