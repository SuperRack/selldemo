package com.example.selldemo2.enums;



public enum  OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHEN(1,"完结"),
    CANCEL(2,"已取消"),
    ;
    private Integer code;
    private String message;
    OrderStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
