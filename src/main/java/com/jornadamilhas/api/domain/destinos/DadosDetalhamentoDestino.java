package com.jornadamilhas.api.domain.destinos;

import java.util.Base64;

public record DadosDetalhamentoDestino (
    Long id,
    String nome,
    float preco,
    String foto
){
    public DadosDetalhamentoDestino(Destino destino) {
        this(destino.getId(),
             destino.getNome(),
             destino.getPreco(),
             Base64.getEncoder().encodeToString(destino.getFoto()));
    }    
}
