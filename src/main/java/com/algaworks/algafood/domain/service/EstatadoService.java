package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EstatadoService {
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "O Estado de código %d, não foi encontrado";
    public static final String MSG_ESTADO_EM_USO = "O Estado de código %d, está sendo usado";
    @Autowired
    EstadoRepository estadoRepository;

    public Estado validaEstado(Long id) {
        return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));
    }

    public Estado salvar(@RequestBody Estado cozinha) {
        estadoRepository.save(cozinha);
        return cozinha;
    }

    public void excluir(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }

}