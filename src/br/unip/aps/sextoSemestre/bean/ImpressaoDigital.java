package br.unip.aps.sextoSemestre.bean;

import java.util.Arrays;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import java.io.Serializable;

public class ImpressaoDigital implements Serializable
{
    private DPFPFingerIndex descricaoDedo;
    private byte[] template;
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ImpressaoDigital && obj.hashCode() == this.hashCode();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + ((this.descricaoDedo != null) ? this.descricaoDedo.hashCode() : 0);
        hash = 97 * hash + Arrays.hashCode(this.template);
        return hash;
    }
    
    public ImpressaoDigital() {
    }
    
    public ImpressaoDigital(final DPFPFingerIndex descricaoDedo, final byte[] template) {
        this.descricaoDedo = descricaoDedo;
        this.template = template;
    }
    
    public DPFPFingerIndex getDescricaoDedo() {
        return this.descricaoDedo;
    }
    
    public void setDescricaoDedo(final DPFPFingerIndex descricaoDedo) {
        this.descricaoDedo = descricaoDedo;
    }
    
    public byte[] getTemplate() {
        return this.template;
    }
    
    public void setTemplate(final byte[] template) {
        this.template = template;
    }
}
