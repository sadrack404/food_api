package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    protected EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
