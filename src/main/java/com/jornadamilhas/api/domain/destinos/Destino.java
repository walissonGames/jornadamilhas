package com.jornadamilhas.api.domain.destinos;

import java.util.Base64;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "destino")
@Entity(name = "Destino")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Destino {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private float preco;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] foto;
    
    public Destino(DadosCadastroDestino dados) {
    
        System.out.println(dados.preco());
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.foto = Base64.getDecoder().decode(dados.foto());
    }
    
    public void atualizarInformacoes(DadosAtualizacaoDestino dados){
        if(dados.preco() > 0)
            this.preco = dados.preco();
        if(dados.nome() != null)
            this.nome = dados.nome();
        if(dados.foto() != null)
            this.foto = Base64.getDecoder().decode(dados.foto());
    }
}
