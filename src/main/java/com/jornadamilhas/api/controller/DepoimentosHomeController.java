package com.jornadamilhas.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornadamilhas.api.domain.depoimentos.DadosDetalhamentoDepoimento;
import com.jornadamilhas.api.domain.depoimentos.DepoimentoRepository;

@RestController
@RequestMapping("depoimentos-home")
public class DepoimentosHomeController {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    @GetMapping()
    public ResponseEntity<Page<DadosDetalhamentoDepoimento>> recuperar(@PageableDefault(size = 3) Pageable paginacao) {
        var depoimentos = depoimentoRepository.retornarDepoimentosAleatorios(paginacao)
                          .map(depoimento -> new DadosDetalhamentoDepoimento(depoimento));
        
        return ResponseEntity.ok(depoimentos);
    }
}
