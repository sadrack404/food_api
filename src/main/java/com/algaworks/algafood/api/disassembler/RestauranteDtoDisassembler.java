package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDtoDisassembler {
    @Autowired
    ModelMapper modelMapper;

    public Restaurante toDtoObject(RestauranteInput restauranteInput){
        return modelMapper.map(restauranteInput, Restaurante.class);
    }


      /*  public Restaurante toDtoObject(RestauranteInputDto restauranteInputDto) {
            Restaurante restaurante = new Restaurante();
            restaurante.setNome(restauranteInputDto.getNome());
            restaurante.setTaxaFrete(restauranteInputDto.getTaxaFrete());

            Cozinha cozinha = new Cozinha();
            cozinha.setId(restauranteInputDto.getCozinha().getId());

            restaurante.setCozinha(cozinha);
            return restaurante;}
       */


    public void copyToDtoObject(RestauranteInput restauranteInput, Restaurante restaurante){
        /*Evitar Resolved [org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2; nested exception is org.hibernate.HibernateException*/
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInput, restaurante);
    }
}
