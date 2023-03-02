package com.algaworks.algafood.domain.exception;

import java.io.Serial;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
