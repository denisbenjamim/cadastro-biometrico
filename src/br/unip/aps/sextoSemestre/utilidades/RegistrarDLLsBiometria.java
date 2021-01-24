package br.unip.aps.sextoSemestre.utilidades;

import java.io.File;

public class RegistrarDLLsBiometria {
   
    private RegistrarDLLsBiometria(String pathDll) {
        String oldPath = System.getProperty("java.library.path");
        String updatePath = String.format("%s%s%s",oldPath,File.pathSeparator, pathDll);
        System.setProperty("java.library.path", updatePath);    
    }    
    
    public static RegistrarDLLsBiometria build(String pathDll){
        return new RegistrarDLLsBiometria(pathDll);
    }
}