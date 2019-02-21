package com.frz.rippleimage;

/**
 * author: siney
 * Date: 2019/2/21
 * description:
 */
public class Utils {
    public static int[] hexArgb2Decimal(String argb){
        char[] chars = argb.toUpperCase().toCharArray();
        int[] res = null;
        if(chars[0] == '#' && chars.length == 7){
            StringBuilder sb = new StringBuilder(argb);
            sb.insert(1, "FF");
            chars = sb.toString().toCharArray();
        }
        if(chars[0] == '#' && chars.length == 9){
            res = new int[4];
            for(int i = 0;i < 4;i++){
                char a = chars[2 * i + 1];
                char b = chars[2 * i + 2];
                res[i] = (int) (hex2Decimal(a, 1) + hex2Decimal(b, 0));
            }
        }
        return res;
    }

    private static double hex2Decimal(char hex, int m){
        if(hex >= 'A' && hex <= 'F'){
            return (hex - 'A' + 10) * Math.pow(16, m);
        }else{
            return (hex - '0') * Math.pow(16, m);
        }
    }
}
