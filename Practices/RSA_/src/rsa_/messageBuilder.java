/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;
import javax.crypto.Cipher;

/**
 *
 * @author rsagr
 */
public class messageBuilder {
    private File fileText;
    private File fileKey;
    private String action;
    private String keyAes;
    private String hashResult;
    private static PublicKey finalPublicKey;
    private static PrivateKey finalPrivateKey;
    
    public messageBuilder(File ft,File fk,String mode,String key){
        this.fileText = ft;
        this.fileKey = fk;
        this.action = mode;
        this.keyAes = key;
    }
    public void startAction() throws Exception{
        /*First we select the file we are gonna encript and we make a hash */
        hashResult = getHashID(fileText);
        System.out.println(hashResult);
        /*Now we apply the RSA method with the privateKey*/
        
    }
    public String getHashID(File fileText) throws Exception{
        String sha1;
        String content = new String(Files.readAllBytes(Paths.get(fileText.getPath())));
        MessageDigest md =  MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(content.getBytes("UTF-8"));
        sha1 = byteToHex(md.digest());
        return sha1;
    }
    private static String byteToHex(final byte[] hash){
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
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
    public static void generateKeys() throws Exception{
        /*This method generates the public and private Keys and the use the 
        method SaveFiles to storges the keys*/
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        //KeyPair keyPair = buildKeyPair();
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        finalPublicKey = publicKey;
        finalPrivateKey = privateKey;
        SaveFiles(publicKey,privateKey);
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
    public String getFileName(){
        return fileText.getPath();
    }
    public String getFileKeyName(){
        return fileKey.getPath();
    }
    public String getAction(){
        return action;
    }
    public String getKeyValue(){
        return keyAes;
    }
    public String getFileInfo(String path)throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded,StandardCharsets.UTF_8);
    }
}
