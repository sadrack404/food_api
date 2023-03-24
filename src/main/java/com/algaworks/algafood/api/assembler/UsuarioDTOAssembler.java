package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public UsuarioDTO toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
        return usuarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
