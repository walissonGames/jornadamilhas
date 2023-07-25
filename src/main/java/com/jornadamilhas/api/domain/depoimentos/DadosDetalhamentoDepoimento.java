package com.jornadamilhas.api.domain.depoimentos;

import java.util.Base64;

public record DadosDetalhamentoDepoimento(
    Long id,
    String nome,
    String depoimento,
    String foto
) {
    private static String imageToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
    
    public DadosDetalhamentoDepoimento(Depoimento depoimento) {
        this(depoimento.getId(),
             depoimento.getNome(),
             depoimento.getDepoimento(),
             imageToBase64(depoimento.getFoto())
        );
    }
}
