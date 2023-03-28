package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends NegocioException {

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
