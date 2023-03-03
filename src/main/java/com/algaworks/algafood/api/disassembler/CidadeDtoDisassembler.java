package com.algaworks.algafood.api.disassembler;


import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDtoDisassembler {
    @Autowired
    ModelMapper modelMapper;

    public Cidade toDtoObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDtoObject(CidadeInput cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
