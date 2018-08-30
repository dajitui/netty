package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static Map map=new HashMap();

    public void bb(){
        map.put("1","2");
    }

    public static void main(String[] args) {
        String s="哈哈|你好123456";
        String[] s1=s.split("\\|",3);

        System.out.println(s.substring(s1[0].length()+1,s.length()));
    }
}
