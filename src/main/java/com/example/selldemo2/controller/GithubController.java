package com.example.selldemo2.controller;

import com.example.selldemo2.config.GithubConfig;
import com.example.selldemo2.dataobject.SellerInfo;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.reponsitory.SellerInfoRe;
import com.example.selldemo2.utils.AuthUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/github")
public class GithubController {
    @Autowired
    private GithubConfig githubConfig;
    @Autowired
    private SellerInfoRe sellerInfoRe;

    private static String callback="http://localhost:8080/sell/github/userInfo";
    @RequestMapping("/authorize")
    public String authorize(){
        String clientid=githubConfig.getClientId();
        String redirectUrl="https://github.com/login/oauth/authorize?client_id="+clientid+"&state=123&redirect_uri="+callback;
        return "redirect:"+redirectUrl;
    }
    @RequestMapping("/userInfo")
    public String userinfo(@RequestParam("code") String code,
                                 Map<String, Object> map) throws Exception{
        String clientid=githubConfig.getClientId();
        String clientsecret=githubConfig.getClinetSecret();
        String redirectUrl = "https://github.com/login/oauth/access_token?" +
                "client_id=" + clientid + "&client_secret=" + clientsecret + "&code=" + code;
        String token=AuthUtil.doGetJosn(redirectUrl);
        String infourl="https://api.github.com/user?"+token;
        String userinfo=AuthUtil.doGetJosn(infourl);
        JSONObject userjson=JSONObject.fromObject(userinfo);
        System.out.println(userjson);
        SellerInfo sellerInfo = new SellerInfo();
        if (userjson !=null) {
            sellerInfo.setId(userjson.getString("id"));
            sellerInfo.setOpenid(userjson.getString("node_id"));
            sellerInfo.setUsername(userjson.getString("login"));
            sellerInfo.setPassword("123456");
            sellerInfoRe.save(sellerInfo);
        }else{
            System.out.println("异常");
        }
        String returnUrl="/seller/login";
        return "redirect:" + returnUrl + "?openid=" + sellerInfo.getOpenid();
    }
}
