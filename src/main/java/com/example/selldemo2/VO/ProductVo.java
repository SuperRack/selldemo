package com.example.selldemo2.VO;

import com.example.selldemo2.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/*
商品包含类目
 */
public class ProductVo implements Serializable {
    private static final long serialVersionUID = -3343839967675146655L;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public List<ProductInfoVo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfoVo> productInfoList) {
        this.productInfoList = productInfoList;
    }
}
