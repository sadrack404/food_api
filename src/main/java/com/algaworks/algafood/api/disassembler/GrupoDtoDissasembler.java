package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.api.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDtoDissasembler {

    @Autowired
    ModelMapper modelMapper;

    public Grupo toDtoObject (GrupoInputDto grupoInputDto) {
        return modelMapper.map(grupoInputDto, Grupo.class);
    }

    public void copyToDtoObject (GrupoInputDto grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
