package com.algaworks.algafood.domain.exception;

import java.io.Serial;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;
    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
