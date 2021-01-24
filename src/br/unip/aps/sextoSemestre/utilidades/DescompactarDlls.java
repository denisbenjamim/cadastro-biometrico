package br.unip.aps.sextoSemestre.utilidades;

import br.unip.aps.sextoSemestre.formularios.FormularioPrincipal;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.zip.ZipException;
import java.util.jar.JarFile;
import java.io.File;
import java.nio.file.Files;
import lombok.Getter;

public class DescompactarDlls {
    @Getter
    private File dllStorage;
    
    
    private DescompactarDlls() {
       init();      
    }
    
    private void init(){
        File jar = new File(FormularioPrincipal.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath().replaceAll("%20", " "));
        
        try(JarFile jarFile = new JarFile(jar)) {           
            dllStorage = Files.createTempDirectory("dlls").toFile();            
            
            if(!dllStorage.isDirectory())
                dllStorage.mkdirs();
            
            writeFilesInTemporaryDirectory(jarFile);
            
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    private void writeFilesInTemporaryDirectory(JarFile jarFile){
        jarFile.stream()
            .filter(predicate -> predicate.getName().endsWith(".dll"))
            .forEach(consumer -> {  
                String pathDLL = String.format("%s%s%s",dllStorage.getPath(),File.separator, consumer.getName().split("/")[5]) ;

                try(InputStream is = jarFile.getInputStream(consumer);FileOutputStream fos = new FileOutputStream(pathDLL))  {
                    if (is == null) 
                        throw new ZipException("Ouve um problem ao tentar extrair as dlls");
                    
                    byte[] buffer = new byte[is.available()];
                    int bytesLidos = 0;
                    
                    while((bytesLidos = is.read(buffer))> 0)
                        fos.write(buffer, 0, bytesLidos);                    
                    

                    markFileToDeleteOnExit(pathDLL);                   
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
    }
    private void markFileToDeleteOnExit(String path){
        new File(path).deleteOnExit();    
    }
    
   public static DescompactarDlls build(){
       return new DescompactarDlls();
   }
   
}
