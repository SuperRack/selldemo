package com.example.selldemo2.service;

import com.example.selldemo2.dto.Orderdto;

/**
 * 买家
 */
public interface BuyerService {
    //查询一个订单
    Orderdto findOrderOne(String openid,String orderId);
    //取消订单
    Orderdto cancelOrder(String openid, String orderId);

}
