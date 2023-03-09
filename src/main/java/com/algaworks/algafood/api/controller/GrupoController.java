package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDtoDissasembler;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.api.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDtoAssembler grupoDtoAssembler;

    @Autowired
    private GrupoDtoDissasembler grupoDtoDissasembler;

    @RequestMapping()
    public List<GrupoDto> buscar() {
        return grupoDtoAssembler.toModelList(grupoService.listar());
    }

    @RequestMapping("/{grupoId}")
    public GrupoDto buscar(@PathVariable Long grupoId) {
        return grupoDtoAssembler.toModel(grupoService.validaGrupo(grupoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDto salvar(@RequestBody GrupoInputDto grupoInputDto) {
        Grupo grupo = grupoDtoDissasembler.toDtoObject(grupoInputDto);
        return grupoDtoAssembler.toModel(grupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDto atualizar(@PathVariable Long grupoId, @RequestBody GrupoInputDto grupoInput) {
        Grupo grupo = grupoService.validaGrupo(grupoId);
        grupoDtoDissasembler.copyToDtoObject(grupoInput, grupo);
        return grupoDtoAssembler.toModel(grupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void remover(@PathVariable Long grupoId) {
        grupoService.remover(grupoId);
    }

}