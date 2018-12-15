package asfd;

import java.awt.FileDialog;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author lupita
 */

public class RSA extends javax.swing.JFrame {
    String rutafile;//Archivo a cifrar
    String dfile;//Directorio para guardar el archivo en la misma ruta
    String nfile;//nombre del archivo para concatenar el nombre
    String rutakey;//ruta de la llave
    String tkey;//Tipo de la llave
    String nkey;
    PrivateKey pv;//Llave privada
    PublicKey pb;//Llave pública
    public RSA() {
        rutafile="";
        rutakey="";
        tkey="";
        initComponents();
    }
    public RSA(String rfile,String tpkey, String rkey){
        rutafile=rfile;
        tkey=tpkey;
        rutakey=rkey;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonfile = new javax.swing.JButton();
        jCBop = new javax.swing.JComboBox<>();
        jButtonkey = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCBtkey = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(98, 95, 92));

        jPanel1.setBackground(new java.awt.Color(192, 185, 250));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(7, 23, 33));
        jLabel1.setText("RSA Cipher");

        jButtonfile.setText("Choose the file");
        jButtonfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonfileActionPerformed(evt);
            }
        });

        jCBop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Encrypt", "Decrypt" }));

        jButtonkey.setText("Choose the key");
        jButtonkey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonkeyActionPerformed(evt);
            }
        });

        jButton3.setText("Done");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCBtkey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Public", "Private" }));

        jLabel2.setText("-choose a file-");

        jLabel3.setText("-choose the key-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jCBop, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonfile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonkey)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCBtkey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)))))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(225, 225, 225))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(189, 189, 189))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonfile)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonkey)
                    .addComponent(jCBtkey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jCBop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonfileActionPerformed
        FileDialog fd=new FileDialog(this,"Choose the file",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null){
            dfile=fd.getDirectory();
            nfile=fd.getFile();
            rutafile=dfile+nfile;
            jLabel2.setText(rutafile);
        }
        else
            JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButtonfileActionPerformed

    private void jButtonkeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonkeyActionPerformed
        FileDialog fd=new FileDialog(this,"Choose the file",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null){
            String fdir=fd.getDirectory();
            nkey=fd.getFile();
            rutakey=fdir+nkey; 
            jLabel3.setText(rutakey);
        }
        else
            JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButtonkeyActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //boolean fed=false;
        JOptionPane.showMessageDialog(null, "The file "+nfile+" will be "+jCBop.getSelectedItem()+"ed with the "+jCBtkey.getSelectedItem()+" key "+ nkey,"Info",JOptionPane.INFORMATION_MESSAGE);
        tkey=(String) jCBtkey.getSelectedItem();
        System.out.println(rutafile);
        System.out.println(rutakey);
        System.out.println(tkey);
        System.out.println(jCBop.getSelectedItem());
        try {
            loadKey(rutakey,tkey);
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(jCBop.getSelectedItem()=="Encrypt"){
            try {
               Encrypt();
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                Decrypt();
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
                Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    public void loadKey(String s, String sk) throws Exception {
        if(sk.equals("Public")){
            FileInputStream fis;
            fis = new FileInputStream(s);
            int n = fis.available();
            byte[] b = new byte[n];
            fis.read(b);
            fis.close();
            KeyFactory kf = KeyFactory.getInstance("RSA");
            KeySpec ks = new X509EncodedKeySpec(b);
            PublicKey kbytes = kf.generatePublic(ks);
            pb = kbytes;
        }
        else{
            FileInputStream fis;
            fis = new FileInputStream(s);
            int n = fis.available();
            byte[] b = new byte[n];
            fis.read(b);
            fis.close();
            KeyFactory kf = KeyFactory.getInstance("RSA");
            KeySpec ks = new PKCS8EncodedKeySpec(b);
            PrivateKey kbytes = kf.generatePrivate(ks);
            pv = kbytes;
        } 
    }
    
    public void Encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException{
        try (InputStream in = new FileInputStream(rutafile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            byte[] ciphertext;
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                while ((bytesRead = in.read(buffer)) != -1){
                    output.write(buffer, 0, bytesRead);
                }   ciphertext = output.toByteArray();
                output.close();
            }
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if(tkey.equals("Private")){
                rsa.init(Cipher.ENCRYPT_MODE, pv);
                //return true;
            }
            else{
                rsa.init(Cipher.ENCRYPT_MODE, pb);
                //return true;
            }
            byte[] c = rsa.doFinal(ciphertext);
            try (FileOutputStream out = new FileOutputStream(dfile+"rsaEnc"+nfile)) {
                out.write(c, 0, c.length);
                out.close();
                in.close();
                JOptionPane.showMessageDialog(null, "Your file have been encrypted, check it on " + dfile+"rsaEnc"+nfile,"File generated",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void Decrypt() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, FileNotFoundException, BadPaddingException, IOException{
        try (InputStream in = new FileInputStream(rutafile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            byte[] plaintext;
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                while ((bytesRead = in.read(buffer)) != -1){
                    output.write(buffer, 0, bytesRead);
                }   plaintext = output.toByteArray();
                output.close();
            }
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if(tkey.equals("Private")){
                rsa.init(Cipher.DECRYPT_MODE, pv);
                //return true;
            }
            else{
                rsa.init(Cipher.DECRYPT_MODE, pb);
                //return true;
            }
            byte[] c = rsa.doFinal(plaintext);
            try (FileOutputStream out = new FileOutputStream(dfile+"rsaDec"+nfile)) {
                out.write(c, 0, c.length);
                out.close();
                in.close();
                JOptionPane.showMessageDialog(null, "Your file have been decrypted, check it on " + dfile+"rsaDec"+nfile,"File generated",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RSA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonfile;
    private javax.swing.JButton jButtonkey;
    private javax.swing.JComboBox<String> jCBop;
    private javax.swing.JComboBox<String> jCBtkey;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
