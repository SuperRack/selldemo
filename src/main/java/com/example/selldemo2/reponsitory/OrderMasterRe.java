package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.OrderMaster;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderMasterRe extends JpaRepository<OrderMaster,String> {
      Page<OrderMaster> findByBuyerOpenid(String openid,Pageable pageable);
}
