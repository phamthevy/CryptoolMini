/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public abstract class CryptoAlgorithm {
    protected File inputFile;
    protected File inputKey;
    
    
    protected byte[] readFile(File file) throws IOException{
        return Files.readAllBytes(file.toPath());
    }
    
    protected byte[] readKey(File file) throws Exception{
        FileInputStream fs = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        String key = br.readLine();
        return DatatypeConverter.parseHexBinary(key);
    }
    
    /**
     * Set the input file for the algorithm
     * @param input the input file used for en/decrypt 
     */
    public void setInputFile(File input){
        this.inputFile = input;
    }
    
    /**
     * Set the input key file for the algorithm
     * @param key the key text tile used for en/decrypt
     */
    public void setInputKey(File key){
        this.inputKey = key;
    }
    
    
    /**
     * Executing the process encrypting the file, get the hash, and save to file,
     * Showing according progress to a bar
     * @param path the path of the encrypt file
     * @param progressBar the progress bar of encrypting op
     * @param hashFunc the hashFunc
     * @throws Exception if file not found, or Function not found
     */
    public abstract void encrypt(String path) throws Exception;
    public abstract void decrypt (String path) throws Exception;
   
}
