package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.model.FormaDePagamentoDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

    @GetMapping
    public List<FormaDePagamentoDto> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.validaRestaurante(restauranteId);
        return formaPagamentoDtoAssembler.toCollectionDto(restaurante.getFormasDePagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}