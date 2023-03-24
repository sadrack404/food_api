package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeDTODisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
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
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    private CidadeDTODisassembler cidadeDTODisassembler;

    @GetMapping
    public List<CidadeDTO> listarTodasCidade() {
        return cidadeDTOAssembler.toCollectionDTO(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeDTO listarCidadePorId(@PathVariable Long id) {
        Cidade cidade = cidadeService.verificaCidadeId(id);
        return cidadeDTOAssembler.toModel(cidade);
    }

    @PostMapping
    public CidadeDTO salvarCidade(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeDTODisassembler.toDomainObject(cidadeInput);
            return cidadeDTOAssembler.toModel(cidadeService.adicionarUmaCidade(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO alterarCidade(@PathVariable Long id, @Valid @RequestBody CidadeInput cidade) {
        try {
            Cidade cidadeNova = cidadeService.verificaCidadeId(id);
            cidadeDTODisassembler.copyToDomainObject(cidade, cidadeNova);
            return cidadeDTOAssembler.toModel(cidadeService.adicionarUmaCidade(cidadeNova));
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