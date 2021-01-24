package br.unip.aps.sextoSemestre.bean;

import java.util.Set;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Usuario implements Serializable{
    private String nome;
    private String CPF;
    private byte[] foto;
    private Set<ImpressaoDigital> impressaoDigital;    
}
