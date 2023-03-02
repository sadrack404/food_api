package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,
        RestauranteRepositoryQuerys, JpaSpecificationExecutor<Restaurante> {

    /* Prefixos de pesquisa */
    /* FindBy - Head - Get - Query - Stream */

    //@Query("from Restaurante where nome like %:nome% and cozinha_id =: id")
    //List<Restaurante> consultaPorNome(String nome, @Param("id") Long cozinha);

    @Query("")
    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long CozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);
}