/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.keygen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class RSAKeyGen implements KeyGen{
    private KeyPairGenerator kpg;
    private int keyLength = 2048;

    public RSAKeyGen() {
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    @Override
    public void writeToFile(String path) {
        FileWriter fw = null;
        FileWriter fw1 = null;
        try {
            KeyPair kp = genKeyPair();
            byte[] publicKey = kp.getPublic().getEncoded();
            byte[] privateKey = kp.getPrivate().getEncoded();
            
            
            
            File publicKeyFile = new File(path + "//publicKey.txt");
            File privateKeyFile = new File(path + "//privateKey.txt");
            
            fw = new FileWriter(publicKeyFile);
            fw.write(DatatypeConverter.printBase64Binary(publicKey));
            System.out.println(publicKeyFile);
            
            fw1 = new FileWriter(privateKeyFile);
            fw1.write(DatatypeConverter.printBase64Binary(privateKey));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            try {
                fw.close();
                fw1.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
    }
    
    private KeyPair genKeyPair(){
        kpg.initialize(keyLength);
        return kpg.genKeyPair();
    } 
    
    private String parseToString(byte[] input){
        return DatatypeConverter.printBase64Binary(input);
    }
    
}
