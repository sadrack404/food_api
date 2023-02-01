package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class FotoProduto {

    @Column(name = "foto_produto_nome")
    private String nome;
    @Column(name = "foto_produto_descricao")
    private String descricao;
    @Column(name = "foto_produto_contentype")
    private String contentType;
    @Column(name = "foto_produto_tamanho")
    private Long tamanho;

}