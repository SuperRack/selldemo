package com.example.selldemo2.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 时间+随机数
     * @return
     */
    public static synchronized String genkey(){
        Random random=new Random();
        Integer a=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(a);
    }

}
