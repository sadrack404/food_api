package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstatadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Estado listaUmEstado(@PathVariable Long id) {
        return estadoService.validaEstado(id);
    }

    @PostMapping
    public Estado adicionaEstado(@RequestBody Estado estado) {
        return estadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    public Estado alterarEstado(@PathVariable Long id, @RequestBody Estado estado) {
        var estadoNovo = estadoService.validaEstado(id);
        BeanUtils.copyProperties(estado, estadoNovo, "id");
        return estadoRepository.save(estadoNovo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEstado(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}