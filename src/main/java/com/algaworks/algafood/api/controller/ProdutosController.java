package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoDTODisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutosController {

    @Autowired
    public RestauranteService restauranteService;

    @Autowired
    public ProdutoService produtoService;

    @Autowired
    public ProdutoDTOAssembler produtoDTOAssembler;

    @Autowired
    public ProdutoDTODisassembler produtoDTODisassembler;

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscaProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var produto = produtoService.buscaProdutoRestaurante(restauranteId, produtoId);
        return produtoDTOAssembler.produtoModeloToModel(produto);
    }

    @GetMapping
    public List<ProdutoDTO> listaPodutos(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.validaRestaurante(restauranteId);
        return produtoDTOAssembler.produtoModeloList(restaurante.getProduto());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionaProduto(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.validaRestaurante(restauranteId);
        Produto produto = produtoDTODisassembler.toDTOObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = produtoService.adicionar(produto);
        return produtoDTOAssembler.produtoModeloToModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO aletrarProduto (@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody ProdutoInput produtoInput) {
        Produto produto = produtoService.validarProduto(restauranteId, produtoId);
        produtoDTODisassembler.copyToDTOObject(produtoInput, produto);
        return produtoDTOAssembler.produtoModeloToModel(produtoService.adicionar(produto));
    }
}