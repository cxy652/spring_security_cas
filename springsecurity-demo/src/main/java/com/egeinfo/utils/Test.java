package com.egeinfo.utils;

import org.springframework.util.Base64Utils;

public class Test {
    public static void main(){
        String str = "123456";
        String str1 = Base64Utils.encodeToString(str.getBytes());

        System.out.println(str1);
    }

}
