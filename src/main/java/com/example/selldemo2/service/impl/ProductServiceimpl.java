package com.example.selldemo2.service.impl;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.dataobject.ProductInfo;
import com.example.selldemo2.dto.CartDTO;
import com.example.selldemo2.enums.ProductStatusEnum;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.reponsitory.ProductInfoRe;
import com.example.selldemo2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductInfoRe reponsitory;


    @Override
    public ProductInfo findOne(String productId) {
        return reponsitory.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return reponsitory.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return reponsitory.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return reponsitory.save(productInfo);
    }
    //加库存
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
          ProductInfo productInfo= reponsitory.findOne(cartDTO.getProductId());
            if(productInfo ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            reponsitory.save(productInfo);
        }
    }
    //减库存
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=reponsitory.findOne(cartDTO.getProductId());
            if(productInfo ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
           Integer result= productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result <0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            reponsitory.save(productInfo);
        }
    }
    //上架
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo=reponsitory.findOne(productId);
        if(productInfo ==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getproductenum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return reponsitory.save(productInfo);
    }
    //在架
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=reponsitory.findOne(productId);
        if(productInfo ==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getproductenum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return reponsitory.save(productInfo);
    }
}
