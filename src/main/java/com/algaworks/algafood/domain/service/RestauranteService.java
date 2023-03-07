package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RestauranteService {
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Restaurante de id %d não enconttrado";
    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d, não pode ser removida pois está em uso";
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaService cozinhaService;

    public Restaurante validaRestaurante(Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.validaCozinha(cozinhaId);
        restaurante.setCozinha(cozinha);
        restauranteRepository.save(restaurante);
        return restaurante;
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.inativar();
    }

    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
        }
    }
}
