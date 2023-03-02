package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteDtoAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteDtoDisassembler;
import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.api.model.input.RestauranteInputDto;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    RestauranteService restauranteService;
    @Autowired
    RestauranteDtoAssembler restauranteDtoAssembler;
    @Autowired
    RestauranteDtoDisassembler restauranteDtoDisassembler;


    @GetMapping
    public List<RestauranteDto> listar() {
        return restauranteDtoAssembler.toCollectionDto(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDto buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.validaRestaurante(id);
        return restauranteDtoAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionarRestaurante(@RequestBody @Valid RestauranteInputDto restauranteInputDto) {
        try {
            Restaurante restaurante = restauranteDtoDisassembler.toDtoObject(restauranteInputDto);
            return restauranteDtoAssembler.toModel(this.restauranteService.salvar(restaurante));
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDto alterarRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteInputDto restauranteInputDto) {
        try {
            Restaurante restaurante = restauranteDtoDisassembler.toDtoObject(restauranteInputDto);
            var restauranteNovo = this.restauranteService.validaRestaurante(id);


            BeanUtils.copyProperties(restaurante, restauranteNovo, "id", "formasDePagamento", "endereco", "dataCadastro", "dataAtualizacao");
            return restauranteDtoAssembler.toModel(restauranteService.salvar(restauranteNovo));
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void excluirRestaurante(@PathVariable Long id) {
        restauranteService.excluir(id);
    }
}