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
import java.util.Optional;

@Service
public class CozinhaService {
    //classe de serviço só retonar NEGOCIO
    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Cozinha> listarTodasCozinhas() {
        return cozinhaRepository.findAll();
    }

    public Optional<Cozinha> encontrarPorId(Long id) {
        return cozinhaRepository.findById(id);
    }

    public Cozinha validaCozinha(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Restaurante de id %d não enconttrado", id)
                ));
    }

    public Cozinha salvar(@RequestBody Cozinha cozinha) {
        cozinhaRepository.save(cozinha);
        return cozinha;
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Cozinha de código %d, não encontrada", id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de código %d, não pode ser removida pois está em uso", id)
            );
        }
    }
}
