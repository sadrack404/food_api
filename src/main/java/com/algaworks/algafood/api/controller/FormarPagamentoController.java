package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoDtoDisassembler;
import com.algaworks.algafood.api.model.FormaDePagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaDePagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormarPagamentoController {
    @Autowired
    FormaDePagamentoService formaDePagamentoService;

    @Autowired
    FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

    @Autowired
    FormaPagamentoDtoDisassembler formaDePagamentoDtoDisassembler;

    @GetMapping
    public List<FormaDePagamentoDto> listar() {
        return formaPagamentoDtoAssembler.toCollectionDto(formaDePagamentoService.listar());
    }

    @GetMapping("/{id}")
    public FormaDePagamentoDto buscar(@PathVariable @Valid Long id) {
        FormaPagamento formaPagamento = formaDePagamentoService.validaFormaPagamento(id);
        return formaDePagamentoDtoDisassembler.toDtoObject(formaPagamento);
    }

    @PostMapping
    public FormaDePagamentoDto salvar(@RequestBody @Valid FormaDePagamentoDto formaDePagamentoDto) {
        FormaPagamento formaPagamento = formaPagamentoDtoAssembler.toModel(formaDePagamentoDto);
        return formaDePagamentoDtoDisassembler.toDtoObject(formaDePagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FormaDePagamentoDto atualizar(@PathVariable @Valid Long id, @RequestBody @Valid FormaDePagamentoDto formaDePagamentoDto) {
        FormaPagamento formaPagamento = formaPagamentoDtoAssembler.toModel(formaDePagamentoDto);
        formaPagamento.setId(id);
        return formaDePagamentoDtoDisassembler.toDtoObject(formaDePagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable @Valid Long id) {
        formaDePagamentoService.deletar(id);
    }
}
