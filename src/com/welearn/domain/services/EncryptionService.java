/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.welearn.domain.services;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author weiwei2017
 */
public class EncryptionService {
    
    private static final String key = "5367566B58703273357638792F423F45";
    
    public static String encrypt(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(plain.getBytes());
        return new String(encrypted);
    }
    
//    public static void main(String[] args)
//    {
//        try {
//            System.out.println(encrypt("helloworld"));
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(EncryptionService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchPaddingException ex) {
//            Logger.getLogger(EncryptionService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidKeyException ex) {
//            Logger.getLogger(EncryptionService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalBlockSizeException ex) {
//            Logger.getLogger(EncryptionService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (BadPaddingException ex) {
//            Logger.getLogger(EncryptionService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
