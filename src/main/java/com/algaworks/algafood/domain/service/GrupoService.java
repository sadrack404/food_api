package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    public Grupo validaGrupo(Long id) {
        return grupoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Grupo não encontrado")
        );
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Transactional
    public void remover(Long id) {
        grupoRepository.deleteById(id);
    }

}