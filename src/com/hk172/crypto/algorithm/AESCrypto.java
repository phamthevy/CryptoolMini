/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author binhchiu
 */
public class AESCrypto extends CryptoAlgorithm{

    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    private final int BUFF_SIZE = 16384;
    public AESCrypto(){
        super();
        try {
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    

    @Override
    public void encrypt(String path) throws Exception {
        byte[] key = readKey(inputKey);
        
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        SecretKey sKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(sKey.getEncoded());
        encryptCipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
        
        CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            fis.read(inputBuffer, 0, unitsize);
            cos.write(inputBuffer,0, unitsize);
            read += unitsize;
            
        }
        cos.close();
        
    }

    @Override
    public void decrypt(String path) throws Exception {
        byte[] key = readKey(inputKey);
        
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        SecretKey skey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(skey.getEncoded());
        decryptCipher.init(Cipher.DECRYPT_MODE, skey, iv);
        
        CipherOutputStream cos = new CipherOutputStream(fos, decryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
           
            fis.read(inputBuffer,0,unitsize);          
            cos.write(inputBuffer, 0, unitsize);
            read += unitsize;
            
        }
        cos.close();
        fis.close();
    }
    
}
