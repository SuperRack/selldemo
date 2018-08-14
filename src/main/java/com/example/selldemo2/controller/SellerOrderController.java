package com.example.selldemo2.controller;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 订单列表
     * page 第1页开始
     * size 一页有多少数据
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest request=new PageRequest(page-1,size);
        Page<Orderdto> orderdtoPage=orderService.findList(request);
        map.put("orderdtoPage",orderdtoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,String> map){
        try {
            Orderdto orderdto = orderService.findOne(orderId);
            orderService.cancel(orderdto);
        }catch (SellException e) {
            System.out.println("【卖家端取消订单】发现异常"+e);
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CNACEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
    /**
     * 订单详情
     */
    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        Orderdto orderdto=new Orderdto();
        try{
           orderdto= orderService.findOne(orderId);
        }catch (SellException e){
            System.out.println("【卖家端查询订单详情】发生异常"+e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderdto",orderdto);
        return new ModelAndView("order/detail",map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String, Object> map){
        try {
            Orderdto orderdto = orderService.findOne(orderId);
            orderService.finish(orderdto);
        }catch (SellException e){
            System.out.println("【卖家端完结订单】发生异常"+e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
}
}
