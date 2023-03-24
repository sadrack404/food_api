package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public ProdutoDTO toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(List<Produto> lista) {
        return lista.stream().map(this::toModel).toList();
    }
}
