package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.disassembler.EstadoDTODisassembler;
import com.algaworks.algafood.api.model.EstadoDTO;
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
    private EstadoRepository estadoRepository;

    @Autowired
    private EstatadoService estadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoDTODisassembler estadoDTODisassembler;

    @GetMapping
    public List<EstadoDTO> lista() {
        return estadoDTOAssembler.toCollectionDTO(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoDTO listaUmEstado(@PathVariable Long id) {
        return estadoDTOAssembler.toModel(estadoService.validaEstado(id));
    }

    @PostMapping
    public EstadoDTO adicionaEstado(@RequestBody EstadoInput estadoInput) {
        Estado estado = estadoDTODisassembler.toDomainObject(estadoInput);
        return estadoDTOAssembler.toModel(estadoService.salvar(estado));
    }

    @PutMapping("/{id}")
    public EstadoDTO alterarEstado(@PathVariable Long id, @RequestBody EstadoInput estado) {
        var estadoNovo = estadoService.validaEstado(id);
        estadoDTODisassembler.copyToDomainObject(estado, estadoNovo);
        return estadoDTOAssembler.toModel(estadoRepository.save(estadoNovo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEstado(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}