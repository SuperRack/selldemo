package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.SellerInfo;
import com.example.selldemo2.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoReTest {
    @Autowired
    private SellerInfoRe sellerInfoRe;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtil.genkey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        SellerInfo result = sellerInfoRe.save(sellerInfo);
        Assert.assertNotNull(result);
    }




    @Test
    public void findByOpenid() throws Exception {
        SellerInfo result = sellerInfoRe.findByOpenid("abc");
        System.out.println(result);
        Assert.assertEquals("abc", result.getOpenid());
    }

}