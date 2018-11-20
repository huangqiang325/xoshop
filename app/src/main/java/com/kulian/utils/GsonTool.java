package com.kulian.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoqiang on 2017/12/2.
 */

public class GsonTool {
    public static String  getGosnString(InputStream is){
        try {
            ByteArrayOutputStream boa=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer=new byte[1024];

            while((len=is.read(buffer))!=-1){
                boa.write(buffer,0,len);
            }
            is.close();
            boa.close();
            byte[] result=boa.toByteArray();
            String temp=new String(result);
//识别编码
            if(temp.contains("utf-8")){
                return unicodeToString(new String(result,"utf-8"));
            }else if(temp.contains("gb2312")){
                return unicodeToString(new String(result,"gb2312"));
            }else{
                return unicodeToString(new String(result,"utf-8"));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch+"" );

        }

        return str;

    }
}
