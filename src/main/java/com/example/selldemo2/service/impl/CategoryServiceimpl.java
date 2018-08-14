package com.example.selldemo2.service.impl;

import com.example.selldemo2.dataobject.ProductCategory;
import com.example.selldemo2.reponsitory.ProductcategoryRe;
import com.example.selldemo2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private ProductcategoryRe reponsitory;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return reponsitory.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return reponsitory.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return reponsitory.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return reponsitory.save(productCategory);
    }
}
