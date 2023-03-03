package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDtoAssembler {

    @Autowired
    ModelMapper modelMapper;


    public CidadeDto toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public List<CidadeDto> toCollectionDto(List<Cidade> cidades) {
        return cidades.stream().map(this::toModel).collect(Collectors.toList());
    }

}
