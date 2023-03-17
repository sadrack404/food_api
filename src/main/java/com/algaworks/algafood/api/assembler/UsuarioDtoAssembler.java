package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDtoAssembler {

    @Autowired
    ModelMapper modelMapper;

    public UsuarioDto toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public List<UsuarioDto> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
