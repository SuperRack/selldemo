package com.example.selldemo2.service;

import com.example.selldemo2.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
   ProductCategory findOne(Integer categoryId);
   List<ProductCategory> findAll();
   List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
   ProductCategory save(ProductCategory productCategory);

}
