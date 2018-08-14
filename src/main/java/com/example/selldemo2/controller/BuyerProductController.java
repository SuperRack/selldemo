package com.example.selldemo2.controller;


import com.example.selldemo2.VO.ProductInfoVo;
import com.example.selldemo2.VO.ProductVo;
import com.example.selldemo2.VO.ResultVo;
import com.example.selldemo2.dataobject.ProductCategory;
import com.example.selldemo2.dataobject.ProductInfo;
import com.example.selldemo2.service.CategoryService;
import com.example.selldemo2.service.ProductService;
import com.example.selldemo2.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVo list(){
        //1查询所有的上架的商品
        List<ProductInfo> productInfolist=productService.findUpAll();

        //2查询类目（一次性查询）
        //lambda表达式来遍历
        List<Integer> categoryTypelist=productInfolist.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypelist);
        //3数据拼装
        List<ProductVo> productVoList=new ArrayList<>();
         for (ProductCategory productCategory:productCategoryList){
            ProductVo productVo=new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVoList=new ArrayList<>();
            for (ProductInfo productInfo:productInfolist) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultUtils.success(productVoList);
    }

}
