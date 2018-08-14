package com.example.selldemo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixin")
public class WeixinController {
    @RequestMapping("/auth")
    public void auth(@RequestParam("code") String code){
        System.out.println("进入auth方法-----");

    }

}
