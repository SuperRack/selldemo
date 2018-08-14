package com.example.selldemo2.service.impl;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.service.BuyerService;
import com.example.selldemo2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceimpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public Orderdto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public Orderdto cancelOrder(String openid, String orderId) {
        Orderdto orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO ==null){
            System.out.println("【取消订单】查不到该订单"+orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private Orderdto checkOrderOwner(String openid, String orderId) {
        Orderdto orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            System.out.println("【查询订单】订单的openid不一致. openid={}, orderDTO={}"+openid+ orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
