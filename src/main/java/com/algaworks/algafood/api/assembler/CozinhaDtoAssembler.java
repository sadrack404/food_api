package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDtoAssembler {

    @Autowired
    ModelMapper modelMapper;

    public CozinhaDto toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDto.class);
    }

    public List<CozinhaDto> toCollectionDto(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(this::toModel).collect(Collectors.toList());
    }

}
