package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private List<FormaPagamento> formasDePagamento = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDate dataCadastro;

    @JsonIgnore
    private LocalDate dataAtualizacao;
    @JsonIgnore
    private List<Produto> produto = new ArrayList<>();

}