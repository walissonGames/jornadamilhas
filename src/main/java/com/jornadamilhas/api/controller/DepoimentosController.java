package com.jornadamilhas.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jornadamilhas.api.domain.depoimentos.DadosAtualizacaoDepoimento;
import com.jornadamilhas.api.domain.depoimentos.DadosCadastroDepoimento;
import com.jornadamilhas.api.domain.depoimentos.DadosDetalhamentoDepoimento;
import com.jornadamilhas.api.domain.depoimentos.Depoimento;
import com.jornadamilhas.api.domain.depoimentos.DepoimentoRepository;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("depoimentos")
public class DepoimentosController {
    
    @Autowired
    private DepoimentoRepository depoimentoRepository;
    
    @PostMapping
    public ResponseEntity<Depoimento> cadastrar(@RequestBody DadosCadastroDepoimento dados, UriComponentsBuilder uriBuilder) throws IOException {
        var depoimento = new Depoimento(dados);
        depoimentoRepository.save(depoimento);
        var uri = uriBuilder.path("/depoimentos/{id}").buildAndExpand(depoimento.getId()).toUri();
        
        return ResponseEntity.created(uri).body(depoimento);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoDepoimento> recuperar(@PathVariable Long id) {
        var depoimento = depoimentoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoDepoimento(depoimento));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoDepoimento> atualizar(@RequestBody DadosAtualizacaoDepoimento dados) throws IOException {
        var depoimento = depoimentoRepository.getReferenceById(dados.id());
        depoimento.atualizarInformacoes(dados);
        
        return ResponseEntity.ok(new DadosDetalhamentoDepoimento(depoimento));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        depoimentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
