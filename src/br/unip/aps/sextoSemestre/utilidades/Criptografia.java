// 
// Decompiled by Procyon v0.5.36
// 

package br.unip.aps.sextoSemestre.utilidades;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import java.security.Key;
import javax.crypto.SealedObject;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class Criptografia<Type> {
    private Cipher algoritimo;
    private byte[] chave;
    private SecretKeySpec sks;
    private File pastaArquivos;
    
    public Criptografia() {
        this.chave = "chave de 16bytes".getBytes();
        this.sks = new SecretKeySpec(this.chave, "AES");
        this.pastaArquivos = new File("Usuarios Cadastrados");
        try {
            this.pastaArquivos.mkdir();
            this.algoritimo = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException ex) {
           
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", 0);
        }
    }
    
    public Type descriptografar(final SealedObject so) {
        try {
            this.algoritimo.init(2, this.sks);
            return (Type) so.getObject(this.algoritimo);
        }
        catch (BadPaddingException | ClassNotFoundException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
           
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", 0);
            return null;
        }
    }
    
    public SealedObject criptografar(final Object objeto) {
        try {
            this.algoritimo.init(1, this.sks);
            final SealedObject so = new SealedObject((Serializable)objeto, this.algoritimo);
            return so;
        }
        catch (IllegalBlockSizeException | InvalidKeyException | IOException ex) {
          
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", 0);
            return null;
        }
    }
    
    public void salvarComoArquivoCriptografado(final Object objeto, final String nomeObjecto) {
        final File tmp = new File("Usuarios Cadastrados/" + nomeObjecto + ".ADDD");
        try {
            final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmp));
            oos.writeObject(this.criptografar(objeto));
            oos.close();
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", 0);
            ex.printStackTrace();
        }
    }
    
    public Type recuperarArquivoCriptografado(final File tmp) {
        try {
            final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tmp));
            final SealedObject so = (SealedObject)ois.readObject();
            return this.descriptografar(so);
        }
        catch (ClassNotFoundException | IOException ex) {
           
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", 0);
            ex.printStackTrace();
            return null;
        }
    }
    
    public File getPastaArquivos() {
        return this.pastaArquivos;
    }
}
