package com.monditech.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	
	private static String algorithm = "AES";
	private static byte[] keyValue = new byte[] {'0','2','3','4','0','6','7','8','9','1','2','3','4','5','6','7'};

	public static String encrypt(String plainText) throws Exception {
    	
        Key key = generateKey();
        Cipher chiper = Cipher.getInstance(algorithm);
        chiper.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = chiper.doFinal(plainText.getBytes());
        
        return new Base64().encodeAsString(encVal); // BASE64Encoder().encodeBuffer(encVal);
            
    }

	public static String decrypt(String encryptedText) throws Exception {
		
        Key key = generateKey();
        Cipher chiper = Cipher.getInstance(algorithm);
        chiper.init(Cipher.DECRYPT_MODE, key);
        
        byte[] decordedValue = new Base64().decode(encryptedText);//new BASE64Decoder().decodeBuffer(encryptedText);
        return new String(chiper.doFinal(decordedValue));
    
    }

    private static Key generateKey() throws Exception  {
		
    	return new SecretKeySpec(keyValue, algorithm);
            
    }

}