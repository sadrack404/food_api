package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaDTODisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    private CozinhaDTODisassembler cozinhaDTODisassembler;


    @GetMapping
    public List<CozinhaDTO> listar() {
        return cozinhaDTOAssembler.toCollectionDTO(cozinhaService.listarTodasCozinhas());
    }

    @GetMapping("/{id}")
    public CozinhaDTO buscar(@PathVariable Long id) {
        return cozinhaDTOAssembler.toModel(cozinhaService.validaCozinha(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adiciona(@RequestBody @Valid CozinhaInput cozinha) {
        var cozinhaSalva = cozinhaDTODisassembler.toDomainObject(cozinha);
        return cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaSalva));
    }

    @PutMapping("/{id}")
    public CozinhaDTO alterar(@PathVariable Long id, @RequestBody CozinhaInput cozinha) {
        var cozinhaNova = cozinhaService.validaCozinha(id);
        cozinhaDTODisassembler.copyToDomainObject(cozinha, cozinhaNova);
        return cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaNova));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }

}