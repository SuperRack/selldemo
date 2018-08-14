package com.example.selldemo2.service.impl;

import com.example.selldemo2.dataobject.SellerInfo;
import com.example.selldemo2.reponsitory.SellerInfoRe;
import com.example.selldemo2.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceimpl implements SellerService {
    @Autowired
    private SellerInfoRe sellerInfoRe;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRe.findByOpenid(openid);
    }
}
