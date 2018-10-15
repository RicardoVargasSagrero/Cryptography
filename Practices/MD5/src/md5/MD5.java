/**
 * This is an example of MD5 (Message Digest) using java libraries
 * The example below generates hash for a password and then 
 * verifies it
 */
package md5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import java.util.Scanner;   
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author Ricardo Vargas Sagrero
 */
public class MD5 {

    /**
     * @param args the command line arguments
     */
    static File ff;
    public void openFile(){
        JFileChooser jf = new JFileChooser();
        int a = jf.showOpenDialog(null);
        System.out.println(a);
        if(a == JFileChooser.APPROVE_OPTION){
            try {
                ff = jf.getSelectedFile();
                System.out.println("Nombre del archivo \n" + ff.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println(ff.getPath());
            }
       }
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        // TODO code application logic here
        
        String hash = "F5D080D4F4E185DECA8A8B24F72408D9";
        String [] keys = {"9A1BA7F38A3E8D8F9DDD55972868CB3F","17185CEF199E1C89804EDEE9DCDD1B90","F5D080D4F4E185DECA8A8B24F72408D9"};
        String password = "NoSuchPassword";
        File file = null;
        JFileChooser ff = new JFileChooser();
        int a = ff.showOpenDialog(null);
        if(a == JFileChooser.APPROVE_OPTION){
            try {
                file = ff.getSelectedFile();
                JOptionPane.showMessageDialog(ff, a);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println(file.getPath());
            }
        }   
        BufferedReader fr = null;
        fr = new BufferedReader(new FileReader(file.getPath()));
        String line = null;
        int i = 0; 
        //This is the funtion that Java implement in java.security.MessageDigest
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        while((line = fr.readLine()) != null){
            System.out.println(line);
            md5.update(line.getBytes());
            byte [] digests = md5.digest();
            String hashs = DatatypeConverter.printHexBinary(digests).toUpperCase();
            if(keys[i].equals(hashs)){
                System.out.println("CORRECT!\nThe hash created is the same as the hash saved ");
            }
            else{
                System.out.println("ERROR!\nThere was a mistake, the hash create doesn't mach the hash saved");
            }
            i++;
        } 
        fr.close();
        /**In conclusion we can use the MD5 for digest a words and then same them
         * is a DataBase, this with the function that if the DB is committed the
         * passwords will not be there
         */
    }
    
}
