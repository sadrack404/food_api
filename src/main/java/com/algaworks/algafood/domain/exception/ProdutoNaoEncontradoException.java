package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends NegocioException{
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
