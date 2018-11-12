/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_;

import java.io.File;

/**
 *
 * @author rsagr
 */
public class messageBuilder {
    private File fileText;
    private File fileKey;
    private String action;
    
    public messageBuilder(File ft,File fk,String mode){
        this.fileText = ft;
        this.fileKey = fk;
        this.action = mode;
    }
    public void startAction(){
        
    }
}
