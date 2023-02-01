package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteComNomeSemalhanteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/JPA")
public class TesteJPA {
    @Autowired
    private CozinhaRepository cozinha;
    @Autowired
    private RestauranteRepository restaurante;

    @GetMapping("/cozinha/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome) {
        return cozinha.findTodasCozinhasByNome(nome);
    }

    @GetMapping("/cozinha/unica-por-nome")
    public Optional<Cozinha> cozinhaPorNome(String nome) {
        return cozinha.findByNome(nome);
    }

    @GetMapping("/restaurante/por-taxa-frete")
    public List<Restaurante> listaPorFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restaurante.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurante/por-nome")
    public List<Restaurante> listaPorFrete(String nome, Long cozinhaId) {
        return restaurante.findByNomeContainingAndCozinhaId(nome, cozinhaId);
    }

    @GetMapping("/restaurante/primeiro-por-nome")
    public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
        return restaurante.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurante/query")
    public List<Restaurante> nomeETaxa
            (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restaurante.finda(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/restaurante/com-frete-gratis")
    public List<Restaurante> nomeETaxa2(String nome) {
        var comFreteGratis = new RestauranteComFreteGratisSpec();
        var comNomeSemelhante = new RestauranteComNomeSemalhanteSpec(nome);
        return restaurante.findAll(comFreteGratis.and(comNomeSemelhante));
    }
}