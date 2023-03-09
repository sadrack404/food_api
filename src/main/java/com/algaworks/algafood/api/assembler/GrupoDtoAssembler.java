package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDtoAssembler {

    @Autowired
    ModelMapper modelMapper;

    public GrupoDto toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDto.class);
    }

    public List<GrupoDto> toModelList(List<Grupo> grupos) {
        return grupos.stream().map(this::toModel).collect(Collectors.toList());
    }
}
