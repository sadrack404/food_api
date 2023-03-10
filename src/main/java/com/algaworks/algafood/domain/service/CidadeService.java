package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_NAO_CADASTRADO = "Cidade com %d não existente";
    public static final String MSG_CIDADE_EM_USO = "Cidade com id %d está em uso";
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    EstatadoService estatadoService;


    public Cidade verificaCidadeId(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_CADASTRADO, id)));
    }

    @Transactional
    public Cidade adicionarUmaCidade(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estatadoService.validaEstado(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluirCidade(Long id) {
        try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_CADASTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }
}