package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoDTODisassembler;
import com.algaworks.algafood.api.model.FormaDePagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormarPagamentoController {
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private FormaPagamentoDTODisassembler formaPagamentoDTODisassembler;

    @GetMapping
    public List<FormaDePagamentoDTO> listar() {
        return formaPagamentoDTOAssembler.toCollectionDTO(formaPagamentoService.listar());
    }

    @GetMapping("/{id}")
    public FormaDePagamentoDTO buscar(@PathVariable @Valid Long id) {
        FormaPagamento formaPagamento = formaPagamentoService.validaFormaPagamento(id);
        return formaPagamentoDTODisassembler.toDomainObject(formaPagamento);
    }

    @PostMapping
    public FormaDePagamentoDTO salvar(@RequestBody @Valid FormaDePagamentoDTO formaDePagamentoDto) {
        FormaPagamento formaPagamento = formaPagamentoDTOAssembler.toModel(formaDePagamentoDto);
        return formaPagamentoDTODisassembler.toDomainObject(formaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FormaDePagamentoDTO atualizar(@PathVariable @Valid Long id, @RequestBody @Valid FormaDePagamentoDTO formaDePagamentoDto) {
        FormaPagamento formaPagamento = formaPagamentoDTOAssembler.toModel(formaDePagamentoDto);
        formaPagamento.setId(id);
        return formaPagamentoDTODisassembler.toDomainObject(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable @Valid Long id) {
        formaPagamentoService.deletar(id);
    }
}
