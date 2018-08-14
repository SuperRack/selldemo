package com.example.selldemo2.aspect;

import com.example.selldemo2.SellException.SellerAuthorizeException;
import com.example.selldemo2.constant.CookieConstant;
import com.example.selldemo2.constant.RedisConstant;
import com.example.selldemo2.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.example.selldemo2.controller.Seller*.*(..))"+
    "&& !execution(public * com.example.selldemo2.controller.SellerUserController.*(..))")
    public void verify(){
    }

    @Before("verify()")
    public void doverify(){
        //RequestContextHolder持有上下文的Request容器
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= attributes.getRequest();

        //查询cookie
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie ==null){
            throw new SellerAuthorizeException();
        }
        //查询redis
        String tokenvalue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_KEY,cookie.getValue()));
        if (StringUtils.isEmpty(tokenvalue)) {
            throw new SellerAuthorizeException();
        }
    }

}
