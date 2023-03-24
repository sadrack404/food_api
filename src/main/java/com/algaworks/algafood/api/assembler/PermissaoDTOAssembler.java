package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public PermissaoDTO toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toListModel(Collection<Permissao> permissao) {
        return permissao.stream().map(this::toModel).toList();
    }
}
