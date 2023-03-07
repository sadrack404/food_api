package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class RestauranteDto {
    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private CozinhaDto cozinha;
    private Boolean ativo;
    private FormaDePagamentoDto formaDePagamento;

}