package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.disassembler.EstadoDtoDisassembler;
import com.algaworks.algafood.api.model.EstadoDto;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstatadoService;
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

    @Autowired
    public EstadoDtoAssembler estadoDtoAssembler;

    @Autowired
    public EstadoDtoDisassembler estadoDtoDisassembler;

    @GetMapping
    public List<EstadoDto> lista() {
        return estadoDtoAssembler.toCollectionDto(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoDto listaUmEstado(@PathVariable Long id) {
        return estadoDtoAssembler.toDto(estadoService.validaEstado(id));
    }

    @PostMapping
    public EstadoDto adicionaEstado(@RequestBody EstadoInput estadoInput) {
        Estado estado = estadoDtoDisassembler.toDtoObject(estadoInput);
        return estadoDtoAssembler.toDto(estadoService.salvar(estado));
    }

    @PutMapping("/{id}")
    public EstadoDto alterarEstado(@PathVariable Long id, @RequestBody EstadoInput estado) {
        var estadoNovo = estadoService.validaEstado(id);
        estadoDtoDisassembler.copyToDtoObject(estado, estadoNovo);
        return estadoDtoAssembler.toDto(estadoRepository.save(estadoNovo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEstado(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}