/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockcipher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.System.arraycopy;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.bouncycastle.jcajce.provider.symmetric.AES;

/**
 *
 * @author rsagr
 */
public class BlockCipherStruct {
    private BufferedImage gImage;
    private File ff;
    private File newff = null;
    private byte [] header;
    private int index;
    private int indexA;
    private String [] modes = {"DES/ECB/PKCS5Padding",
                       "DES/CBC/PKCS5Padding",
                       "DES/CFB/PKCS5Padding",
                       "DES/OFB/PKCS5Padding", //Falta el 8 en OFB8
                       "DES/CTR/PKCS5Padding",
                       "AES/ECB/PKCS5Padding",
                       "AES/CBC/PKCS5Padding",
                       "AES/CFB/PKCS5Padding",
                       "AES/OFB/PKCS5Padding",
                       "AES/CTR/PKCS5Padding"};
    private String modeSelected = "";
    private String nameOutFile = "";
    private String nameAux = "";
    private String command = "";
    private String skfS = "";
    private String key = "";
    private String finalStringCont;
    private static byte [] ivDES = {'+','_','-','b','$','#','=','R'};
    private static byte [] ivAES = {'#','F','9',')','(','^',',','/','$','{','5','a','@',':','e','l'};
    public BlockCipherStruct(int indexMode,int indexAlgorithm,File f,String keyGiven,String cmd){
        index = indexMode;
        indexA = indexAlgorithm;
        ff = f;
        key = keyGiven;
        command = cmd;
    }
    public void SelectedMode(){
        //Select a mode of operation
        if(index == 1){
            skfS = "DES";
            switch(indexA){
                case 1:
                    modeSelected = modes[0];
                    nameAux = "DES_ECB";
                    break;
                case 2:
                    modeSelected = modes[1];
                    nameAux = "DES_CBC";
                    break;
                case 3:
                    modeSelected = modes[2];
                    nameAux = "DES_CFB";
                    break;
                case 4:
                    modeSelected = modes[3];
                    nameAux = "DES_OFB";
                    break;
                default:
                    modeSelected = modes[4];
                    nameAux = "DES_CTR";
            }
        }
        else{
            skfS = "AES";
            switch(indexA){
                case 1:
                    modeSelected = modes[5];
                    nameAux = "AES_ECB";
                    break;
                case 2:
                    modeSelected = modes[6];
                    nameAux = "AES_CBC";
                    break;
                case 3:
                    modeSelected = modes[7];
                    nameAux = "AES_CFB";
                    break;
                case 4:
                    modeSelected = modes[8];
                    nameAux = "AES_OFB";
                    break;
                default:
                    modeSelected = modes[9];
                    nameAux = "AES_CTR";
                    break;
            }
        }
        System.out.println("Mode Selected: "+modeSelected+"\nnameAux: "+nameAux+"\nKey: "+key);
        System.out.println("File path"+ff.getPath());
    }
    private void FileOutName(){
        if(command.contains("-c")){
            nameOutFile = "E"+nameAux+".bmp"; 
        }
        else if(command.contains("-d")){
            nameOutFile = "D"+nameAux+".bmp";
        }
        System.out.println("Name of the new file: "+nameOutFile);
    }
    private byte[] getHeader(byte[] contented){
        byte [] cab = new byte[54];
        int i;
        for(i = 0; i < 54; i++){
            cab[i] = contented[i];
        }
        return cab;
    }
    private byte[] clrHeader(byte[] clr){
        byte [] aux = new byte[clr.length-54];
        int i;
        for(i = 0; i < clr.length-54; i++){
            aux[i] = clr[i+54];
        }
        return aux;
    }
    private void createImage(byte[] c){
        try{
            Files.write(newff.toPath(),c);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private String strConcat(byte [] fS){
        //From here we use the cipher to encrypt or decrypt finalString, after doing that we need to contatened the header with the result
        //using arraycopyFuntion
        //funtion to print all the bytes from an array 
        //byte[] byteArray = new byte[] { -1, -128, 1, 127 };
        //System.out.println(Arrays.toString(byteArray));
        byte[] contated = new byte[54+fS.length];
        arraycopy(header,0,contated,0,header.length);
        arraycopy(fS,0,contated,54,fS.length);
        System.out.println(Arrays.toString(contated));
        newff = new File(nameOutFile);
        createImage(contated);
        return finalStringCont;
    }
    private byte[] Action(byte[] fString) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
        /***
         * This is where all the encryption and decryption begins.
         * For ECB (Electronic codebook), we don't use a vector so at the 
         * begging we and a statement to create the vector or not.
         * CBC (Cipher-Block Chaining)
         * CFB (Cipher FeedBack)
         * OFB (Output feedBack)
         * CTR (Counter Mode encryption)
         */
        byte [] encryptedString;
        System.out.println("Before it goes on the if's: "+nameAux);
        if(nameAux.equals("AES_ECB") || nameAux.equals("DES_ECB")){
            byte[] keyByte = key.getBytes();
            SecretKeySpec ss = new SecretKeySpec(keyByte,skfS);
            Cipher ci = Cipher.getInstance(modeSelected);
            //if index = 1 is encrypt mode 
            if(command.contains("-c")){
                ci.init(Cipher.ENCRYPT_MODE, ss);
                encryptedString = ci.doFinal(fString);
                return encryptedString;
            }
            else{
                ci.init(Cipher.DECRYPT_MODE,ss);
                encryptedString = ci.doFinal(fString);
                return encryptedString;
            }
        }else{
            //Statements
            IvParameterSpec ivspec = null;
            if(index == 1){
                ivspec = new IvParameterSpec(ivDES);
            }
            else{
                System.out.println("Entre a AES para IV  skfS: "+skfS);
                ivspec = new IvParameterSpec(ivAES);
            }
            
            byte[] keyByte = key.getBytes();
            SecretKeySpec skey = new SecretKeySpec(keyByte, skfS);
            //SecretKeyFactory ss = SecretKeyFactory.getInstance(skfS);
            //SecretKey securekey = ss.generateSecret(skey);
            Cipher ci = Cipher.getInstance(modeSelected);
            if(command.contains("-c")){
                ci.init(Cipher.ENCRYPT_MODE,skey,ivspec);
                encryptedString = ci.doFinal(fString);
                return encryptedString;
            }
            else{
                ci.init(Cipher.DECRYPT_MODE,skey,ivspec);
                encryptedString = ci.doFinal(fString);
                return encryptedString;
            }
        }
    }
    public void cipherAction() throws IOException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException{
        SelectedMode();
        FileOutName();
        byte[] finalString = Files.readAllBytes(ff.toPath());
        header = getHeader(finalString);
        //System.out.println(Arrays.toString(header));
        //System.out.println(Arrays.toString(finalString));
        finalString = clrHeader(finalString);
        
        /*Encrypt and decrypt funtion*/
        byte[] encryptedString;
        encryptedString = Action(finalString);
        System.out.println("final concat");
        System.out.println(Arrays.toString(encryptedString));
        finalStringCont = strConcat(encryptedString);
        
    }
    public BufferedImage getNewImage(File ff){
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File(ff.getPath()));
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error opening the file: "+e);
        }
        return image;
    }
    
}
