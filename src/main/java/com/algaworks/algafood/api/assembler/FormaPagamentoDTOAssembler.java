package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FormaDePagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOAssembler {
    @Autowired
    ModelMapper modelMapper;

    public FormaPagamento toModel (FormaDePagamentoDTO dto) {
        return modelMapper.map(dto, FormaPagamento.class);
    }

    public List<FormaDePagamentoDTO> toCollectionDTO(Collection<FormaPagamento> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, FormaDePagamentoDTO.class))
                .collect(Collectors.toList());
    }


}
