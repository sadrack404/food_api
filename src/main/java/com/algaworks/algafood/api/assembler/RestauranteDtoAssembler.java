package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class RestauranteDtoAssembler {

    @Autowired
    ModelMapper modelMapper;

    public RestauranteDto toModel(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteDto.class);
    }

    public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
    }

}
