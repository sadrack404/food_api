package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    RestauranteService restauranteService;

    public Produto validarProduto(Long restauanteId, Long produtoId) {
        return produtoRepository.findById(restauanteId, produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradoException("Produto não encontrado"));
    }

    public Produto buscaProdutoRestaurante(Long restauranteId, Long produtoId) {
        Restaurante restaurante = restauranteService.validaRestaurante(restauranteId);
        for (Produto produto : restaurante.getProduto()) {
            if (produto.getId().equals(produtoId)) {
                return produto;
            }
        }
        throw new ProdutoNaoEncontradoException("Produto não encontrado no restaurante.");
    }

    @Transactional
    public Produto adicionar(Produto produto) {
        return produtoRepository.save(produto);
    }

}
