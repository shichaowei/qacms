package com.wsc.qa.utils;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/*
 * MD5 算法
 */
public class MD5Util {

    public static String getMD5Code(String strObj) {
        return Hashing.md5().hashString(strObj, StandardCharsets.UTF_8).toString();
    }

    public static void main(String[] args) {
        String ming = "{'mobilePhone':'18622222222','password':'123456','userSource':'XD00009','timestamp':'1476079337294'}";
        System.out.println(getMD5Code(ming));
    }
}