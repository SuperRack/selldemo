package com.example.selldemo2.service.impl;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.converter.OrderForm2OrderDto;
import com.example.selldemo2.converter.OrderMaster2OrderDto;
import com.example.selldemo2.dataobject.OrderDetail;
import com.example.selldemo2.dataobject.OrderMaster;
import com.example.selldemo2.dataobject.ProductInfo;
import com.example.selldemo2.dto.CartDTO;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.OrderStatusEnum;
import com.example.selldemo2.enums.PayStatusEnum;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.reponsitory.OrderDetailRe;
import com.example.selldemo2.reponsitory.OrderMasterRe;
import com.example.selldemo2.service.OrderService;
import com.example.selldemo2.service.ProductService;
import com.example.selldemo2.service.WebSocket;
import com.example.selldemo2.utils.KeyUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderServiceimpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRe  orderDetailRe;
    @Autowired
    private OrderMasterRe orderMasterRe;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public Orderdto create(Orderdto orderDTO) {
        String orderid=KeyUtil.genkey();

        BigDecimal orderamout=new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
           ProductInfo productInfo= productService.findOne(orderDetail.getProductId());
           if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
            //2.计算订单总价
            orderamout=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderamout);
            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genkey());
            orderDetail.setOrderId(orderid);
            orderDetailRe.save(orderDetail);
        }
        // 3.写入订单数据库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderid);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderamout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRe.save(orderMaster);
        //4.扣库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(
                e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public Orderdto findOne(String orderId) {
       OrderMaster orderMaster= orderMasterRe.findOne(orderId);
        if(orderMaster ==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=orderDetailRe.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAL_NOT_EXIST);
        }
        Orderdto orderdto=new Orderdto();
        BeanUtils.copyProperties(orderMaster,orderdto);
        orderdto.setOrderDetailList(orderDetailList);
        return orderdto;
    }

    @Override
    public Page<Orderdto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRe.findByBuyerOpenid(buyerOpenid,pageable);
        List<Orderdto> orderdtoList=OrderMaster2OrderDto.converter(orderMasterPage.getContent());
        return new PageImpl<Orderdto>(orderdtoList,pageable,orderMasterPage.getTotalElements());
    }
    //取消订单
    @Override
    @Transactional
    public Orderdto cancel(Orderdto orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【取消订单】订单状态不正确--"+orderDTO.getOrderStatus()+"--"+orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult= orderMasterRe.save(orderMaster);
        if (updateResult ==null){
            System.out.println("【取消订单】更新失败--"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            System.out.println("【取消订单】订单中无商品详情--"+orderDTO);
            throw new SellException(ResultEnum.ORDER_DETATL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);


        //如何支付，需要退款
        if(orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }

        return orderDTO ;
    }

    @Override
    @Transactional
    public Orderdto finish(Orderdto orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【完结订单】订单状态不正确"+orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHEN.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRe.save(orderMaster);
        if (updateResult ==null){
            System.out.println("【完结订单】更新失败--"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Orderdto paid(Orderdto orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【订单完成】订单状态不正确"+orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            System.out.println("【订单完成】订单支付状态不正确"+orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRe.save(orderMaster);
        if (updateResult ==null){
            System.out.println("【订单完成】更新失败--"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;

    }
    //查询订单列表
    @Override
    public Page<Orderdto> findList(Pageable pageable) {
       Page<OrderMaster> orderMasterPage=orderMasterRe.findAll(pageable);
       List<Orderdto> orderdtoList= OrderMaster2OrderDto
               .converter(orderMasterPage.getContent());
        return new PageImpl<Orderdto>(orderdtoList,pageable,orderMasterPage.getTotalElements());
    }
}
