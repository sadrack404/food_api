package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    CozinhaRepository cozinhaRepository;
    @Autowired
    CozinhaService cozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaService.listarTodasCozinhas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cozinha>> cozinhaId(@PathVariable Long id) {
        if (cozinhaRepository.existsById(id)) {
            Optional<Cozinha> cozinha = cozinhaService.encontrarPorId(id);
            return ResponseEntity.ok(cozinha);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*
    Exemplode de como manipular uma respostas http, utilizando um if por exemplo
        @GetMapping("/ex/{id}")
        public ResponseEntity<Optional<Cozinha>> cozinhaId(@PathVariable Long id){
            Optional<Cozinha> cozinha = cozinhaService.cozinhaId(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cozinha);
        }
    */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adiciona(@RequestBody Cozinha cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> alterar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        var cozinhaNova = cozinhaService.validaCozinha(id);
        if (cozinhaNova != null) {
            BeanUtils.copyProperties(cozinha, cozinhaNova, "id");
            cozinhaService.salvar(cozinhaNova);
            return ResponseEntity.ok(cozinhaNova);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cozinhaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}