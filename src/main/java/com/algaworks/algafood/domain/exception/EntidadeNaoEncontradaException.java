package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "Entidade não encontrada") <- mostra uma msg no erro
public class EntidadeNaoEncontradaException extends ResponseStatusException {
//Construtor flexivel onde podemos passar um status e uma mensagem
//é um modo menos pior de se usar o responseStatusException
    public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }

    //Construtor padrão, onde o THIS vai invocar uma resposta HTTP do construtor a cima
    public EntidadeNaoEncontradaException(String mensagem) {
        this(HttpStatus.NOT_FOUND, mensagem);
    }
}
