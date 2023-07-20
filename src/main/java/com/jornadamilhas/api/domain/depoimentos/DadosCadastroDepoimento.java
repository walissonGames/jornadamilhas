package com.jornadamilhas.api.domain.depoimentos;

import org.springframework.web.multipart.MultipartFile;

public record DadosCadastroDepoimento (
    String nome,
    String depoimento,
    MultipartFile foto
){ }