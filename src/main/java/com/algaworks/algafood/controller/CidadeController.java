package com.algaworks.algafood.controller;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listarTodasCidade() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade listarCidadePorId(@PathVariable Long id) {
        return cidadeService.verificaCidadeId(id);
    }

    @PostMapping
    public Cidade salvarCidade(@RequestBody Cidade cidade) {
        try {
            return cidadeService.adicionarUmaCidade(cidade);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public Cidade alterarCidade(@PathVariable Long id, @RequestBody Cidade cidade) {
        var cidadeNova = cidadeService.verificaCidadeId(id);
        BeanUtils.copyProperties(cidade, cidadeNova, "id");
        try {
            return cidadeService.adicionarUmaCidade(cidadeNova);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCidade(@PathVariable Long id) {
        cidadeService.excluirCidade(id);
    }
}