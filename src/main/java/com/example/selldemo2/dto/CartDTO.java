package com.example.selldemo2.dto;
//购物车
public class CartDTO {
    //商品id
    private String productId;
    //数量
    private Integer productQuantity;
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }
}
