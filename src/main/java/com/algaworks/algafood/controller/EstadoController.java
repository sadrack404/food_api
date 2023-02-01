package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstatadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    public EstadoRepository estadoRepository;

    @Autowired
    public EstatadoService estadoService;

    @GetMapping
    public List<Estado> lista() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Estado> listaUmEstado(@PathVariable Long id) {
        return estadoRepository.findById(id);
    }

    @PostMapping
    public Estado adicionaEstado(@RequestBody Estado estado){
        return estadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> alterarEstado(@PathVariable Long id, @RequestBody Estado estado){
        var estadoNovo = estadoService.validaRestaurante(id);
        if (estado != null){
            BeanUtils.copyProperties(estado, estadoNovo, "id");
            estadoRepository.save(estadoNovo);
            return ResponseEntity.ok(estadoNovo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstado(@PathVariable Long id){
        try {
            estadoService.excluir(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntidadeNaoEncontradaException | EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }
}