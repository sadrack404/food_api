package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);

        return modelMapper;
    }
}
