package com.jornadamilhas.api.domain.depoimentos;

import org.springframework.web.multipart.MultipartFile;

public record DadosAtualizacaoDepoimento (
    Long id,
    String nome,
    String depoimento,
    MultipartFile foto
) { }
