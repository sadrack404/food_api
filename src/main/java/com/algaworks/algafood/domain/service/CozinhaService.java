package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CozinhaService {
    public static final String MSG_COZINHA_NAO_ENCOTRADA =
            "Cozinha de código %d, não encontrada";
    public static final String MSG_COZNHA_EM_USO =
            "Cozinha de código %d, não pode ser removida pois está em uso";

    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Cozinha> listarTodasCozinhas() {
        return cozinhaRepository.findAll();
    }

    public Cozinha validaCozinha(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCOTRADA ,id)));
    }

    public Cozinha salvar(@RequestBody Cozinha cozinha) {
        cozinhaRepository.save(cozinha);
        return cozinha;
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCOTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZNHA_EM_USO, id));
        }
    }
}
