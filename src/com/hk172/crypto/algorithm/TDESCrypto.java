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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

/**
 *
 * @author binhchiu
 */
public class TDESCrypto extends CryptoAlgorithm{
    
    private SecretKeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    private final int BUFF_SIZE = 8192;
    
    public TDESCrypto(){
        super();
        try {
            factory = SecretKeyFactory.getInstance("DESede");
            encryptCipher = Cipher.getInstance("DESede");
            decryptCipher = Cipher.getInstance("DESede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    @Override
    public void encrypt(String path) throws Exception {
        //get the key
        byte[] key = readKey(inputKey);
        //prepare the file
        FileInputStream fs = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        //parse the key, init the cipher
        SecretKey skey = factory.generateSecret(new DESedeKeySpec(key));
        encryptCipher.init(Cipher.ENCRYPT_MODE, skey);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        //wrap the fos stream
        CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);
        //var for encrypt
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
            fs.read(inputBuffer, 0, unitsize);
            cos.write(inputBuffer,0, unitsize);
            
            read += unitsize;
        }
        cos.close();
    }
    
    @Override
    public void decrypt(String path) throws Exception {
        //get the key
        byte[] key = readKey(inputKey);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        //prepare the file 
        FileInputStream fs = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        //prepare the key
        SecretKey skey = factory.generateSecret(new DESedeKeySpec(key));
        decryptCipher.init(Cipher.DECRYPT_MODE, skey);
        
        //wrap the fos into a cipherstream
        
        CipherOutputStream cos = new CipherOutputStream(fos, decryptCipher);
        
        //init value for cipher
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        //read and print the value by blocks
        while (read < offset) {
            unitsize = (int) (((offset - read - 8) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
            fs.read(inputBuffer,0,unitsize);
            
            cos.write(inputBuffer, 0, unitsize);
            read += unitsize;
        }
        cos.close();
    }
    
}
