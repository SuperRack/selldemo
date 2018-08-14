package com.example.selldemo2.SellException;

import com.example.selldemo2.enums.ResultEnum;

public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        //实现RuntimeException里的message方法
        super(resultEnum.getMessage());

        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
