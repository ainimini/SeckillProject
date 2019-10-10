package com.miaoshaobject.response;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/10/10
 * @Version 1.0
 **/
public class CommonReturnType {
    /**
     * 表明对应返回的处理结果"success"或"fail"
     */
    private String status;
    /**
     * 如果status=success 则data返回给前端需要的接送数据
     * 如果status=fail 则返回给前端通用的错误码格式
     */
    private Object data;

    /**
     * 定义一个通用的创建方法
      * @return
     */
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
