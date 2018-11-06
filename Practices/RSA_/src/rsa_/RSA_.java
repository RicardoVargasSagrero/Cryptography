/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_;

import java.awt.RenderingHints.Key;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.io.*;
import java.nio.*;
import java.security.*;
import java.security.spec.*;
/**
 *
 * @author rsagr
 * Tutorial
 * https://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml
 * https://community.oracle.com/thread/1528259?start=0&tstart=0
 */
public class RSA_ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, Exception {
        // TODO code application logic here
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        //KeyPair keyPair = buildKeyPair();
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        System.out.println("Public key: "+publicKey.toString()+"\nlength public: "+publicKey.toString().length()+"\nPrivate key: "+privateKey.toString()+"\n lenght of private = "+privateKey.toString().length());
        
        //We have the private and public key, now we'll use the methods
        byte str [] = encrypt(privateKey,"Hola mundo");
        byte strD [] = decrypt(publicKey,str);
        
        System.out.println(new String(str));
        System.out.println(new String(strD));
        /***
         * Saving the public and private key in a File
         */
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
        
        createFile("public_RVS.key",pub.getModulus(),pub.getPublicExponent());
        createFile("private_RVS.key",priv.getModulus(),priv.getPrivateExponent());
 
        PublicKey puk = getPublic("public_RVS.key");
        PrivateKey prk = getPrivate("private_RVS.key");
        
        byte stra[] = encrypt(prk,"Hola mundo");
        byte straa[] = decrypt(puk,stra);
        
        System.out.println(new String(stra));
        System.out.println(new String(straa));
        
        /**
         *We start the encryption and decryption methods 
         */
        byte [] signed = encrypt(privateKey,"This is a secret message therfore we need to understand");
        System.out.println(new String(signed));
        
        //Decryption
        byte [] verified = decrypt(publicKey,signed);
        System.out.println(new String(verified));
    }
    public static void createFile(String filename, BigInteger mod,BigInteger exp) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        try{
            out.writeObject(mod);
            out.writeObject(exp);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  

        return cipher.doFinal(message.getBytes());  
    }
    
    public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        
        return cipher.doFinal(encrypted);
    }
    public static PrivateKey getPrivate(String filename) throws Exception{
        byte [] keyBytes = Files.readAllBytes(Paths.get(filename));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    public static PublicKey getPublic(String filename) throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
