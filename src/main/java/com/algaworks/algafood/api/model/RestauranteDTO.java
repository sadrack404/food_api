package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO {
    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;

}