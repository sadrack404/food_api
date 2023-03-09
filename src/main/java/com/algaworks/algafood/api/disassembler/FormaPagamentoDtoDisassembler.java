package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.FormaDePagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDtoDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public FormaDePagamentoDto toDtoObject(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaDePagamentoDto.class);
    }

    public void copyToDtoObject(FormaPagamento formaPagamento, FormaDePagamentoDto formaDePagamentoDto) {
        modelMapper.map(formaPagamento, formaDePagamentoDto);
    }
}
