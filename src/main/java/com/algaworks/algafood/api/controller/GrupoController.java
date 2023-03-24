package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDtoDissasembler;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.api.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService cadastroGrupo;

    @Autowired
    private GrupoDtoAssembler grupoModelAssembler;

    @Autowired
    private GrupoDtoDissasembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoDto> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return grupoModelAssembler.toModelList(todosGrupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoDto buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDto adicionar(@RequestBody @Valid GrupoInputDto grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDtoObject(grupoInput);
        grupo = cadastroGrupo.salvar(grupo);
        return grupoModelAssembler.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoDto atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInputDto grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
        grupoInputDisassembler.copyToDtoObject(grupoInput, grupoAtual);
        grupoAtual = cadastroGrupo.salvar(grupoAtual);
        return grupoModelAssembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);
    }
}