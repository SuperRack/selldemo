package com.example.selldemo2.controller;



import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.VO.ResultVo;
import com.example.selldemo2.form.OrderForm;
import com.example.selldemo2.converter.OrderForm2OrderDto;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.service.BuyerService;
import com.example.selldemo2.service.OrderService;
import com.example.selldemo2.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @RequestMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        //如何输入信息有误
        if (bindingResult.hasErrors()) {
            System.out.println("【创建订单】参数不正确, orderForm={}"+ orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        Orderdto orderDTO = OrderForm2OrderDto.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            System.out.println("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        Orderdto createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultUtils.success(map);
    }
    //订单列表
    @RequestMapping("/list")
    public ResultVo<List<Orderdto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)) {
            System.out.println("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<Orderdto> orderdtoPage = orderService.findList(openid, request);
        return ResultUtils.success(orderdtoPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVo<Orderdto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
    //TODO
        Orderdto orderdto=buyerService.cancelOrder(openid,orderId);
        return ResultUtils.success(orderdto);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
    //TODO
       buyerService.cancelOrder(openid,orderId);
       return ResultUtils.success();
    }
    }

