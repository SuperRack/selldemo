package com.example.selldemo2.utils;

import com.example.selldemo2.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumclass){
        for (T each:enumclass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
