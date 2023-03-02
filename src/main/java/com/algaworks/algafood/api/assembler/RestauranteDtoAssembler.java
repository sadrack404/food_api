package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CozinhaDto;
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

    /*


    public RestauranteDto toModel(Restaurante restaurante) {
        CozinhaDto cozinhaDto = new CozinhaDto();
        cozinhaDto.setId(restaurante.getCozinha().getId());
        cozinhaDto.setNome(restaurante.getCozinha().getNome());

        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDto.setCozinha(cozinhaDto);

        return restauranteDto;
    }

    */

    public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
    }

}
