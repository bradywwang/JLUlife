package com.brady.jlulife.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by brady on 15-9-19.
 */
public class Utils {
    public static String getMD5Str(String str)  {
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            MessageDigest messageDigest = null;

            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();


            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return md5StrBuff.toString();
    }

}
