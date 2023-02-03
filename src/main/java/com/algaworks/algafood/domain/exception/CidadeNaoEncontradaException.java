package com.algaworks.algafood.domain.exception;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
