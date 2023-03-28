package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.FormaDePagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTODisassembler {

    @Autowired
    ModelMapper modelMapper;

    public FormaDePagamentoDTO toDomainObject(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaDePagamentoDTO.class);
    }

    public void copyToDomainObject(FormaPagamento formaPagamento, FormaDePagamentoDTO formaDePagamentoDto) {
        modelMapper.map(formaPagamento, formaDePagamentoDto);
    }
}
