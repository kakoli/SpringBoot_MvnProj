package com.example.springdata.emp.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashUtil {

    public byte[] MD5HashBytes(String toBeHashed, String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        //if (encoding != null && !"".equals(encoding)) {
        if(StringUtils.isBlank(encoding)) {
            md.update(toBeHashed.getBytes(encoding));
        } else {
            md.update(toBeHashed.getBytes());
        }
        byte[] encBytes = md.digest();
        return encBytes;
    }

    public String byteToHex(byte b) {
        char[] hexDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] array = new char[]{hexDigit[b >> 4 & 15], hexDigit[b & 15]};
        return new String(array);
    }

    public String byteArrayToHex(byte[] array) {
        StringBuffer sb = new StringBuffer();

        for(int k = 0; k < array.length; ++k) {
            sb.append(byteToHex(array[k]));
        }
        return sb.toString();
    }
}
