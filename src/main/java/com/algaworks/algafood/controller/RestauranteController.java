package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        System.out.println(restaurantes.get(0).getNome());
        restaurantes.get(0).getFormasDePagamento().forEach(System.out::println);
        return restaurantes;
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {
        return restauranteService.validaRestaurante(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionarRestaurante(@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante alterarRestaurante(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        var restauranteNovo = this.restauranteService.validaRestaurante(id);
        BeanUtils.copyProperties(restaurante, restauranteNovo, "id", "formasDePagamento", "endereco", "dataCadastro", "dataAtualizacao");
        return restauranteService.salvar(restauranteNovo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void excluirRestaurante(@PathVariable Long id) {
        restauranteService.excluir(id);
    }
}