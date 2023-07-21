package com.jornadamilhas.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jornadamilhas.api.domain.depoimentos.DadosAtualizacaoDepoimento;
import com.jornadamilhas.api.domain.depoimentos.DadosCadastroDepoimento;
import com.jornadamilhas.api.domain.depoimentos.DadosDetalhamentoDepoimento;
import com.jornadamilhas.api.domain.depoimentos.Depoimento;
import com.jornadamilhas.api.domain.depoimentos.DepoimentoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DepoimentosControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private JacksonTester<DadosCadastroDepoimento> dadosCadastroDepoimentoJson;
    
    @Autowired
    private JacksonTester<DadosAtualizacaoDepoimento> dadosAtualizacaoDepoimentoJson;
    
    @Autowired
    private JacksonTester<DadosDetalhamentoDepoimento> dadosDetalhamentoDepoimentoJson;
    
    @MockBean
    private DepoimentoRepository depoimentoRepository;

    @Test
    @DisplayName("Deveria retornar código HTTP 201 e retornar os detalhes do novo objeto criado")
    void testCadastrar() throws Exception {
        var nome = "walisson";
        var texto = "teste automatizado";
        var foto = "/9j/4AAQSkzZ8htRVfb/AFI//9k=";
        
        var response = mvc.perform(
                            post("/depoimentos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosCadastroDepoimentoJson.write(
                                new DadosCadastroDepoimento(nome, texto, foto)).getJson()
                            )
                        ).andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        
        var jsonEsperado = dadosDetalhamentoDepoimentoJson.write(
                                new DadosDetalhamentoDepoimento(null, nome, texto, foto)
                            ).getJson();
        
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado.toString());
    }
    
    @Test
    void testAtualizar() throws Exception {
        var nome = "walisson";
        var texto = "texto atualizado";
        var foto = "/9j/4AAQSkzZ8htRVfb/AFI//9k=";
        
        var depoimento = new Depoimento(new DadosCadastroDepoimento(nome, "texto antigo", foto));
        when(depoimentoRepository.getReferenceById(any())).thenReturn(depoimento);
        
        var response = mvc.perform(
                put("/depoimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAtualizacaoDepoimentoJson.write(
                    new DadosAtualizacaoDepoimento(1L, nome, texto, foto)).getJson()
                )
            ).andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        var jsonEsperado = dadosDetalhamentoDepoimentoJson.write(
                                new DadosDetalhamentoDepoimento(depoimento)
                            ).getJson();
        
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado.toString());
    }

    @Test
    void testRecuperar() throws Exception {
        var nome = "walisson";
        var texto = "teste automatizado";
        var foto = "/9j/4AAQSkzZ8htRVfb/AFI//9k=";
        
        var depoimento = new Depoimento(new DadosCadastroDepoimento(nome, texto, foto));
        when(depoimentoRepository.getReferenceById(any())).thenReturn(depoimento);
        
        var response = mvc.perform(
                            get("/depoimentos/{id}", "1")
                        ).andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        var jsonEsperado = dadosDetalhamentoDepoimentoJson.write(
                                new DadosDetalhamentoDepoimento(null, nome, texto, foto)
                            ).getJson();
        
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado.toString());
    }

    @Test
    void testExcluir() throws Exception {
        var depoimento = new Depoimento(new DadosCadastroDepoimento("", "", "/9j/4AAQSkzZ8htRVfb/AFI//9k="));
        when(depoimentoRepository.getReferenceById(1L)).thenReturn(depoimento);
        
        var response = mvc.perform(
                delete("/depoimentos/{id}", "1")
                ).andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
