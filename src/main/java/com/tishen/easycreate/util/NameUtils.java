package com.tishen.easycreate.util;

/**
 * @Auther: tishe
 * @Date: 12/4/2018 13:38
 * @Description:
 */
public class NameUtils {

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     * @return
     */
    public static String underineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s);
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     * @return
     */
    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

}
