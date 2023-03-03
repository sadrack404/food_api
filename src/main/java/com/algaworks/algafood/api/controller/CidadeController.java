package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.disassembler.CidadeDtoDisassembler;
import com.algaworks.algafood.api.model.CidadeDto;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    CidadeService cidadeService;

    @Autowired
    CidadeDtoAssembler cidadeDtoAssembler;

    @Autowired
    CidadeDtoDisassembler cidadeDtoDisassembler;

    @GetMapping
    public List<CidadeDto> listarTodasCidade() {
        return cidadeDtoAssembler.toCollectionDto(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeDto listarCidadePorId(@PathVariable Long id) {
        Cidade cidade = cidadeService.verificaCidadeId(id);
        return cidadeDtoAssembler.toModel(cidade);
    }

    @PostMapping
    public CidadeDto salvarCidade(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeDtoDisassembler.toDtoObject(cidadeInput);
            return cidadeDtoAssembler.toModel(cidadeService.adicionarUmaCidade(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDto alterarCidade(@PathVariable Long id, @Valid @RequestBody CidadeInput cidade) {
        try {
            Cidade cidadeNova = cidadeService.verificaCidadeId(id);
            cidadeDtoDisassembler.copyToDtoObject(cidade, cidadeNova);
            return cidadeDtoAssembler.toModel(cidadeService.adicionarUmaCidade(cidadeNova));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCidade(@PathVariable Long id) {
        cidadeService.excluirCidade(id);
    }
}