package com.example.selldemo2.converter;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.form.OrderForm;
import com.example.selldemo2.dataobject.OrderDetail;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.ResultEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDto {
    public static Orderdto convert(OrderForm orderForm) {
        Gson gson = new Gson();
        Orderdto orderdto = new Orderdto();
        orderdto.setBuyerName(orderForm.getName());
        orderdto.setBuyerPhone(orderForm.getPhone());
        orderdto.setBuyerAddress(orderForm.getAddress());
        orderdto.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            System.out.println("【对象转换】错误, string={}" + orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderdto.setOrderDetailList(orderDetailList);

        return orderdto;
    }
    }
