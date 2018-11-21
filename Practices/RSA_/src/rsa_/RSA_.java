/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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

// org.bouncycastle
import org.bouncycastle.util.io.pem.PemWriter;
import java.io.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCERSAPublicKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import sun.misc.IOUtils;
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
    static PublicKey finalPublicKey;
    static PrivateKey finalPrivateKey;
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        //KeyPair keyPair = buildKeyPair();
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        SaveFiles(publicKey,privateKey);
        System.out.println("Key are saved in a file");
        finalPublicKey = getPublic("Ricardo_Vargas_Public.pem");
        finalPrivateKey = getPrivate("Ricardo_Vargas_Private.pem");
        byte strss [] = encrypt(privateKey,"Hola mundo de la parka");
        byte strDss [] = decrypt(publicKey,strss);
        System.out.println(new String(strss));
        System.out.println(new String(strDss));
        System.out.println("Public key: "+publicKey.toString()+"\nlength public: "+publicKey.toString().length()+"\nPrivate key: "+privateKey.toString()+"\n lenght of private = "+privateKey.toString().length());
        
        //We have the private and public key, now we'll use the methods
        byte str [] = encrypt(privateKey,"Hola mundo");
        byte strD [] = decrypt(publicKey,str);
        System.out.println(new String(str));
        System.out.println(new String(strD));
        System.out.println("first");
        PublicKey puk = getPublicFile("public_rvargass.pem");
        System.out.println("public key genareted");
        PublicKey pk2 = getPublicFile("public_rvargass.pem");
        System.out.println("Second convertion");
        PrivateKey prk2 = getPrivateFile("private_rvargass.pem");
        
        byte str1[] = encrypt(prk2,"Hola mundo");
        byte strd[] = decrypt(pk2,str1);
        
        System.out.println(new String(str1));
        System.out.println(new String(strd));
        
        /***
         * Saving the public and private key in a File
         */
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
        
        createFile("public_RVS.key",pub.getModulus(),pub.getPublicExponent());
        createFile("private_RVS.key",priv.getModulus(),priv.getPrivateExponent());
        String filenamePublic = "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\public_rvargass.pem";
        String filenamePrivate = "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\private_rvargass.pem";
        Path ppublic = Paths.get(filenamePublic);
        Path pprivate = Paths.get(filenamePrivate);
        //createFile2(ppublic,pprivate,publicKey,privateKey);
        System.out.println("the files are created");
        
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
    public static void SaveFiles(PublicKey publicKey, PrivateKey privateKey) throws FileNotFoundException, IOException{
        // Store Public Key
        final File publicKeyFile = new File("Ricardo_Vargas_Public.pem");
        //publicKeyFile.getParentFile().mkdirs(); // make directories if they do not exist
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(publicKeyFile)) {
            fos.write(x509EncodedKeySpec.getEncoded());
        }

        // Store Private Key.
        final File privateKeyFile = new File("Ricardo_Vargas_Private.pem");
        //privateKeyFile.getParentFile().mkdirs(); // make directories if they do not exist
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(privateKeyFile)) {
            fos.write(pkcs8EncodedKeySpec.getEncoded());
        }
    }

    public static KeyPair LoadKeyPair(String path, String algorithm) throws IOException, NoSuchAlgorithmException,InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(path + "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\public_rvargass.pem");
		FileInputStream fis = new FileInputStream(path + "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\public_rvargass.pem");
		System.out.println("reading file");
                byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
 
		// Read Private Key.
		File filePrivateKey = new File(path + "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\private_rvargass.pem");
		fis = new FileInputStream(path + "C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\private_rvargass.pem");
                byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
                String key = getKey("public_rvargass.pem");
		byte[] decodedKey = Base64.decode("C:\\Users\\rsagr\\Desktop\\7mo\\Cryptography\\Practicas\\RSA_\\public_RVS.key");
                System.out.println(new String(decodedKey));
                // Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                System.out.println("Public key genereted");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
 
	return new KeyPair(publicKey, privateKey);
}
    
    public static String getKey(String filename) throws IOException{
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while((line = br.readLine()) != null){
            strKeyPEM += line;
        }
        br.close();
        return strKeyPEM;
    }
    public static PublicKey getPublicFile(String filename)throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
    public static PrivateKey getPrivateFile(String filename) throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);    
    }
    public static void createFile2(Path filenamePublic,Path filenamePrivate,PublicKey pk,PrivateKey prk) throws IOException{
            try(PemWriter writer = new PemWriter(new FileWriter(filenamePublic.toFile()))){
                writer.writeObject(new PemObject("PUBLIC KEY",pk.getEncoded()));
            }
            try(PemWriter writer = new PemWriter(new FileWriter(filenamePrivate.toFile()))){
                writer.writeObject(new PemObject("PRIVATE KEY",prk.getEncoded()));
            }
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
    public static PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        String temp = new String(keyBytes);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
        //System.out.println("Private key\n"+privKeyPEM);

        Base64 b64 = new Base64();
        byte [] decoded = b64.decode(privKeyPEM);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

   public static PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
      File f = new File(filename);
      System.out.println(f.getName());
      if(f == null){
          System.out.println("Archivo vacio");
      }else{
          System.out.println(f.getAbsolutePath());
      }
      FileInputStream fis = new FileInputStream(f);
      DataInputStream dis = new DataInputStream(fis);
      byte[] keyBytes = new byte[(int) f.length()];
      dis.readFully(keyBytes);
      dis.close();

      String temp = new String(keyBytes);
      String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
      publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");


      Base64 b64 = new Base64();
      byte [] decoded = b64.decode(publicKeyPEM);

      X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
      KeyFactory kf = KeyFactory.getInstance(algorithm);
      return kf.generatePublic(spec);
      }
}
