package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
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
import java.util.Optional;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    RestauranteService restaurante;

    @GetMapping
    public List<Restaurante> listarRestaurantes() {
    List<Restaurante> restaurantes = restauranteRepository.findAll();
        System.out.println(restaurantes.get(0).getNome());
        restaurantes.get(0).getFormasDePagamento().forEach(System.out::println);
        return restaurantes;
    }

    @GetMapping("/{id}")
    public Optional<Restaurante> listarRestaurante(@PathVariable Long id) {
        return restauranteRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionarRestaurante(@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.restaurante.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarRestaurante(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        var restauranteNovo = this.restaurante.validaRestaurante(id);
        if (restauranteNovo != null) {
            BeanUtils.copyProperties(restaurante, restauranteNovo, "id", "formasDePagamento", "endereco", "dataCadastro", "dataAtualizacao");
            this.restaurante.salvar(restauranteNovo);
            return ResponseEntity.ok(restauranteNovo);
        }
        return ResponseEntity.notFound().build();
    }

   /* @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);

        if (restauranteAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual.get());

        return ResponseEntity.ok(restaurante);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirRestaurante(@PathVariable Long id) {
        try {
            restaurante.excluir(id);
            return ResponseEntity.ok().build();
        } catch (EntidadeNaoEncontradaException | EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }
}