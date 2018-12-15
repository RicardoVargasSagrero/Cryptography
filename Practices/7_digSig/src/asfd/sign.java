package asfd;

import java.awt.FileDialog;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author lupita
 */
public class sign extends javax.swing.JFrame {
    String nfile,dfile,nokey,dokey,nrkey,drkey;
    PrivateKey pv;
    PublicKey pb;
    public sign() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbfl = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jbfrm = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jbrec = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Courier 10 Pitch", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("SIGN");

        jbfl.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jbfl.setForeground(new java.awt.Color(0, 0, 0));
        jbfl.setText("Choose the file");
        jbfl.setToolTipText("");
        jbfl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbfl.setContentAreaFilled(false);
        jbfl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbflMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbflMouseEntered(evt);
            }
        });
        jbfl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbflActionPerformed(evt);
            }
        });

        jLabel2.setText("--you haven't chose a file--");

        jbfrm.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jbfrm.setText("Select your key");
        jbfrm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbfrm.setContentAreaFilled(false);
        jbfrm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbfrmMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbfrmMouseEntered(evt);
            }
        });
        jbfrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbfrmActionPerformed(evt);
            }
        });

        jLabel3.setText("--you haven't choose the key--");

        jButton3.setBackground(new java.awt.Color(255, 250, 250));
        jButton3.setFont(new java.awt.Font("Courier 10 Pitch", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(29, 29, 29));
        jButton3.setText("Done");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jbrec.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jbrec.setText("Select the receiver's key");
        jbrec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbrec.setContentAreaFilled(false);
        jbrec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbrecMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbrecMouseEntered(evt);
            }
        });
        jbrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbrecActionPerformed(evt);
            }
        });

        jLabel4.setText("--you haven't choose the receiver key--");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jLabel1)
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbrec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbfl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbfrm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addGap(258, 258, 258))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbfl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbfrm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbrec, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbflMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbflMouseEntered
        jbfl.setForeground(new java.awt.Color(86,86,86));
        
    }//GEN-LAST:event_jbflMouseEntered

    private void jbflMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbflMouseExited
        jbfl.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jbflMouseExited

    private void jbflActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbflActionPerformed
        FileDialog fd=new FileDialog(this,"Choose the file",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null){
            dfile=fd.getDirectory();
            nfile=fd.getFile();
            jLabel2.setText(dfile+nfile);
        }
        else
            JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jbflActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     
        try {
            loadRSAKey(dokey+nokey,"Private");
            loadRSAKey(drkey+nrkey,"Public");
            byte[] md=sha1();
            byte[] ds=RSAEnc(md,"Private");
            byte[] aes=AESEnc();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            baos.write(aes);
            String sep="////";
            baos.write(sep.getBytes());
            baos.write(ds);
            System.out.println(ds.length);
            System.out.println(aes.length);
            byte[] cfile=baos.toByteArray();
            System.out.println(cfile.length);
            SecretKeySpec k=AESkey();
            byte[] ckaes=RSAEnc(k.getEncoded(),"Public");
            System.out.println(ckaes.length);
            if(writeFile(ckaes,dokey,"AESkey.key")){
                JOptionPane.showMessageDialog(null, "The AES key has been saved at: "+dokey+"AESKey.key","Info",JOptionPane.INFORMATION_MESSAGE);
            }
            if(writeFile(cfile,dfile,"Enc"+nfile)){
                JOptionPane.showMessageDialog(null, "The encripted file has been saved at: "+dfile+"Enc"+nfile,"Info",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            Logger.getLogger(sign.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jbfrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbfrmActionPerformed
        FileDialog fd=new FileDialog(this,"Choose the file",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null){
            dokey=fd.getDirectory();
            nokey=fd.getFile();
            jLabel3.setText(dokey+nokey);
        }
        else
        JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jbfrmActionPerformed

    private void jbfrmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbfrmMouseEntered
        jbfrm.setForeground(new java.awt.Color(86,86,86));
    }//GEN-LAST:event_jbfrmMouseEntered

    private void jbfrmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbfrmMouseExited
        jbfrm.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jbfrmMouseExited

    private void jbrecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbrecMouseEntered
        jbfrm.setForeground(new java.awt.Color(86,86,86));
    }//GEN-LAST:event_jbrecMouseEntered

    private void jbrecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbrecMouseExited
        jbfrm.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jbrecMouseExited

    private void jbrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbrecActionPerformed
        FileDialog fd=new FileDialog(this,"Choose the file",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null){
            drkey=fd.getDirectory();
            nrkey=fd.getFile();
            jLabel4.setText(drkey+nrkey);
        }
        else
        JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jbrecActionPerformed
    
    public byte[] sha1() throws Exception{
        byte[] md;
        MessageDigest sha= MessageDigest.getInstance("SHA1");
        md=sha.digest(loadFile());
        return md;
    }
    public byte[] AESEnc()throws Exception{
        byte[] pt=loadFile();
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, AESkey());
        byte[] aes=c.doFinal(pt);
        return aes;
    }
    public byte[] RSAEnc(byte []md,String sk)throws Exception{
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        if(sk.equals("Private"))
            c.init(Cipher.ENCRYPT_MODE, pv);
        else
            c.init(Cipher.ENCRYPT_MODE, pb);
        byte[] rsa=c.doFinal(md);
        return rsa;
    }
    
    public SecretKeySpec AESkey() throws Exception{
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        SecretKeySpec k= new SecretKeySpec(("zapatitozapatito").getBytes(),"AES");
        return k;
    }
    
    public void loadRSAKey(String s, String sk) throws Exception {
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
    
    public byte[] loadFile() throws Exception{
       try (InputStream in = new FileInputStream(dfile+nfile)) {
            byte[] buffer = new byte[10];
            int bytesRead;
            byte[] plaintext;
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                while ((bytesRead = in.read(buffer)) != -1){
                    output.write(buffer, 0, bytesRead);
                }   plaintext = output.toByteArray();
                output.close();
            } 
            return plaintext;
       }
    }
    
    boolean writeFile(byte[]f,String r, String n) throws Exception{
        
        try (FileOutputStream out = new FileOutputStream(r+"Enc"+n)) {
            out.write(f, 0, f.length);
            out.close();
            return true;
        }
    }
   
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new sign().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbfl;
    private javax.swing.JButton jbfrm;
    private javax.swing.JButton jbrec;
    // End of variables declaration//GEN-END:variables
}
