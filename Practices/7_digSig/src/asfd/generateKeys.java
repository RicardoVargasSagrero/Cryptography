/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asfd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.swing.JOptionPane;

/**
 *
 * @author alumno
 */
public class generateKeys {
    public static boolean generateKey(String name) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        KeyPairGenerator kpg  = KeyPairGenerator.getInstance("RSA"); // Se generan claves
        KeyPair kp = kpg.generateKeyPair();
        PublicKey puk = kp.getPublic();
        PrivateKey prk = kp.getPrivate();
        FileOutputStream fospr;
        FileOutputStream fospu;
        byte[] pukbytes = puk.getEncoded();
        byte[] prkbytes = prk.getEncoded();
        fospr = new FileOutputStream(name+"-privatekey.key");
        fospu = new FileOutputStream(name+"-publickey.key");
        fospr.write(prkbytes);
        fospu.write(pukbytes);
        fospr.close();
        fospu.close();
      return true;
    }
      public static void main(String args[]) throws NoSuchAlgorithmException, IOException{
          String nombre;
          nombre=JOptionPane.showInputDialog("Enter your name");
          boolean fc=generateKey(nombre);
          if(fc){
              JOptionPane.showMessageDialog(null, "Your keys have been generated","File generated",JOptionPane.INFORMATION_MESSAGE);
          }
              
      }
    

}
