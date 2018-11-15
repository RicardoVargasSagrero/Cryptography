/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Formatter;

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
}
