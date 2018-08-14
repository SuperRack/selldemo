package com.example.selldemo2.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



public class AuthUtil {
    public static String doGetJosn(String url)throws Exception{
        String result=null;
        DefaultHttpClient client=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response=client.execute(httpGet);
        System.out.println(response);
        HttpEntity entity=response.getEntity();
        if (entity !=null){
             result= EntityUtils.toString(entity,"UTF-8");
        }
        httpGet.releaseConnection();
        return result;
    }


}
