package com.example.selldemo2.service;

import com.example.selldemo2.dto.Orderdto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /** 创建订单. */
    Orderdto create(Orderdto orderDTO);

    /** 查询单个订单. */
    Orderdto findOne(String orderId);

    /** 查询订单列表. */
    Page<Orderdto> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单. */
    Orderdto cancel(Orderdto orderDTO);

    /** 完结订单. */
    Orderdto finish(Orderdto orderDTO);

    /** 支付订单. */
    Orderdto paid(Orderdto orderDTO);

    /** 查询订单列表. */
    Page<Orderdto> findList(Pageable pageable);


}
