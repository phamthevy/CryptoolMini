/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.keygen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class TDESKeyGen implements KeyGen{
    private KeyGenerator keygen ;
    private final int keyLength = 168;
    public TDESKeyGen() {
        try {
            keygen = KeyGenerator.getInstance("DESede");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(String path) {
        FileWriter fw = null;
        try {
            SecretKey skey = genKey();
            String skeyString = parseKeyString(skey);
            File file = new File(path + "//TDESKey.txt");
            fw = new FileWriter(file);
            fw.write(skeyString);
        } catch (IOException ex) {
            Logger.getLogger(TDESKeyGen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(TDESKeyGen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private SecretKey genKey(){
        keygen.init(keyLength);
        return keygen.generateKey();
    }
    
    private String parseKeyString(SecretKey skey){
        byte[] key = skey.getEncoded();
        return DatatypeConverter.printHexBinary(key);
    }
    
}
