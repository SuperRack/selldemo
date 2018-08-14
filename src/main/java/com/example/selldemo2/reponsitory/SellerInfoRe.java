package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SellerInfoRe extends JpaRepository<SellerInfo, String> {
         SellerInfo findByOpenid(String openid);
}
