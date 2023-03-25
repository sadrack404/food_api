package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestauranteService {
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Restaurante de id %d não enconttrado";
    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d, não pode ser removida pois está em uso";
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    public Restaurante validaRestaurante(Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.validaCozinha(cozinhaId);
        Cidade cidade = cidadeService.verificaCidadeId(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.ativar();
    }

    @Transactional
    public void ativar(List<Long>restaurantesId){
        restaurantesId.forEach(this::ativar);
    }


    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.inativar();
    }

    @Transactional
    public void inativar(List<Long>restaurantesId){
        restaurantesId.forEach(this::inativar);
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = validaRestaurante(id);
        restaurante.fechar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaDePagamentoId) {
        Restaurante restaurante = validaRestaurante(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.validaFormaPagamento(formaDePagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaDePagamentoId) {
        Restaurante restaurante = validaRestaurante(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.validaFormaPagamento(formaDePagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
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

    @Transactional
    public void desassociarUsuario(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = validaRestaurante(restauranteId);
        Usuario usuario = usuarioService.valida(usuarioId);
        restaurante.dessassociarUsuario(usuario);
    }

    @Transactional
    public void associarUsuario(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = validaRestaurante(restauranteId);
        Usuario usuario = usuarioService.valida(usuarioId);
        restaurante.associarUsuario(usuario);
    }
}