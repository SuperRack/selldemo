package com.example.selldemo2.converter;

import com.example.selldemo2.dataobject.OrderMaster;
import com.example.selldemo2.dto.Orderdto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//转换器
public class OrderMaster2OrderDto {
//将orderMaster填充到orderdto里
    public static Orderdto converter(OrderMaster orderMaster){
        Orderdto orderdto=new Orderdto();
        BeanUtils.copyProperties(orderMaster,orderdto);
        return orderdto;
    }
    public static List<Orderdto> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
        converter(e)).collect(Collectors.toList());
    }
}
