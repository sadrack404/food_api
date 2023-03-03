package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaDtoDisassembler;
import com.algaworks.algafood.api.model.CozinhaDto;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    CozinhaRepository cozinhaRepository;
    @Autowired
    CozinhaService cozinhaService;

    @Autowired
    CozinhaDtoAssembler cozinhaDtoAssembler;

    @Autowired
    CozinhaDtoDisassembler cozinhaDtoDisassembler;


    @GetMapping
    public List<CozinhaDto> listar() {
        return cozinhaDtoAssembler.toCollectionDto(cozinhaService.listarTodasCozinhas());
    }

    @GetMapping("/{id}")
    public CozinhaDto buscar(@PathVariable Long id) {
        return cozinhaDtoAssembler.toModel(cozinhaService.validaCozinha(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adiciona(@RequestBody @Valid CozinhaInput cozinha) {
        var cozinhaSalva = cozinhaDtoDisassembler.toDtoObject(cozinha);
        return cozinhaDtoAssembler.toModel(cozinhaService.salvar(cozinhaSalva));
    }

    @PutMapping("/{id}")
    public CozinhaDto alterar(@PathVariable Long id, @RequestBody CozinhaInput cozinha) {
        var cozinhaNova = cozinhaService.validaCozinha(id);
        cozinhaDtoDisassembler.copyToDomainObject(cozinha, cozinhaNova);
        return cozinhaDtoAssembler.toModel(cozinhaService.salvar(cozinhaNova));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }

}