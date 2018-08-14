package com.example.selldemo2.utils;

import com.example.selldemo2.VO.ResultVo;

public class ResultUtils {
    public static ResultVo success(Object object){
        ResultVo resultVo=new ResultVo();
        resultVo.setData(object);
        resultVo.setMsg("成功");
        resultVo.setCode(0);
        return resultVo;
    }
    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }

}
