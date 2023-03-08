package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoDto;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        //modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class).addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);
        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value)
        );

        return modelMapper;
    }
}