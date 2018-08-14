package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductcategoryReTest {
    @Autowired
    private ProductcategoryRe reponsitory;


    @Test
    @Transactional
    public void saveTest(){
        List<Integer> list= Arrays.asList(2,3,4);
        List<ProductCategory> result=reponsitory.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());

    }


}