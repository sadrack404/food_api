package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listarTodasCidade() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cidade> listarCidadePorId(@PathVariable Long id) {
        return cidadeRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> salvarCidade(@RequestBody Cidade cidade) {
        try {
            cidadeService.adicionarUmaCidade(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estado não" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> alterarCidade(
            @PathVariable Long id, @RequestBody Cidade cidade) {
        var cidadeNova = cidadeService.verificaCidadeId(id);
        if (cidadeNova != null) {
            BeanUtils.copyProperties(cidade, cidadeNova, "id");
            cidadeService.adicionarUmaCidade(cidadeNova);
            return ResponseEntity.ok(cidadeNova);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> deletarCidade(@PathVariable Long id) {
        try {
            cidadeService.excluirCidade(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}