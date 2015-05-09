package com.utopia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;
public class Md5 {
	
	public static String md5(String messages){
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            byte[] md5=    md.digest();
            
            //转化为明文
            //BASE64Encoder encoder=new BASE64Encoder();
            return md5.toString();//encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }
}
