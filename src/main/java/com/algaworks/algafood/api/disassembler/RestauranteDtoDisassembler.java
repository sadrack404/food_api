package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.RestauranteInputDto;

import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDtoDisassembler {
    @Autowired
    ModelMapper modelMapper;

    public Restaurante toDtoObject(RestauranteInputDto restauranteInputDto){
        return modelMapper.map(restauranteInputDto, Restaurante.class);
    }

    /*public Restaurante toDtoObject(RestauranteInputDto restauranteInputDto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInputDto.getNome());
        restaurante.setTaxaFrete(restauranteInputDto.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDto.getCozinha().getId());

        restaurante.setCozinha(cozinha);
        return restaurante;
    }*/
}
