package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaITex {
/*
* Teste de integração
*/
    @Autowired
    CozinhaService cozinhaService;

    @Test
    public void testarCadastroDeCozinhaComSucesso() {
        //cenário
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Chinesa");
        //ação
        var novaCozinha = cozinhaService.salvar(cozinha);
        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    // @Test(expected =ConstraintViolationException.class)
    @Test
    public void testarCadastroCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);
        assertThrows(ConstraintViolationException.class, () -> {
            cozinhaService.salvar(novaCozinha);
        });
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(1L);
        });
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        assertThrows(CozinhaNaoEncontradaException.class, () -> {
            cozinhaService.excluir(30L);
        });
    }
}