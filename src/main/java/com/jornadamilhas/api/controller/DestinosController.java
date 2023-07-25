package com.jornadamilhas.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jornadamilhas.api.domain.destinos.DadosAtualizacaoDestino;
import com.jornadamilhas.api.domain.destinos.DadosCadastroDestino;
import com.jornadamilhas.api.domain.destinos.DadosDetalhamentoDestino;
import com.jornadamilhas.api.domain.destinos.Destino;
import com.jornadamilhas.api.domain.destinos.DestinoRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("destinos")
public class DestinosController {
    
    @Autowired
    private DestinoRepository destinoRepository;
    
    @PostMapping
    public ResponseEntity<Destino> cadastrar(@RequestBody DadosCadastroDestino dados, UriComponentsBuilder uriBuilder) {
        var destino = new Destino(dados);
        destinoRepository.save(destino);
        var uri = uriBuilder.path("/destinos/{id}").buildAndExpand(destino.getId()).toUri();
        return ResponseEntity.created(uri).body(destino);
    }
    
    @GetMapping()
    public ResponseEntity<Page<DadosDetalhamentoDestino>> recuperar(@RequestParam String nome, @PageableDefault(size = 10) Pageable paginacao) {
        var page = destinoRepository.findAllByNome(nome, paginacao).map(DadosDetalhamentoDestino::new);
        return ResponseEntity.ok(page);
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoDestino> atualizar(@RequestBody DadosAtualizacaoDestino dados) {
        var destino = destinoRepository.getReferenceById(dados.id());
        destino.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoDestino(destino));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        destinoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
