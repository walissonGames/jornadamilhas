package com.jornadamilhas.api.domain.depoimentos;

import java.io.IOException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "depoimento")
@Entity(name = "Depoimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Depoimento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String depoimento;
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] foto;
    
    public Depoimento(DadosCadastroDepoimento dados) throws IOException {
        this.nome = dados.nome();
        this.depoimento = dados.depoimento();
        this.foto = dados.foto().getBytes();
    }

    public void atualizarInformacoes(DadosAtualizacaoDepoimento dados) throws IOException {
        if (dados.nome() != null)
            this.nome = dados.nome();
        if (dados.depoimento() != null)
            this.depoimento = dados.depoimento();
        if (dados.foto() != null)
            this.foto = dados.foto().getBytes();
    }
}
