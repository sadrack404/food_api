package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    //JPA como encontrar todas as cozinhas pelo nome
    List<Cozinha> findTodasCozinhasByNome(String nome);

    //Encontrado a cozinha pelo nome, esse método só
    // funciona caso tenha UM unico nome,
    // ou qualquer outro identificador único
    Optional<Cozinha> findByNome(String nome);

}
