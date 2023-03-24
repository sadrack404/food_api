package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuerys;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuerys {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        //Criteria é usado para consultas JPA complexas
        //Iniciamos usando um builder de criterias
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class); //from Restaurante

        var predicados = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)){
            //Predicados da pesquisa
            predicados.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }
        if (taxaFreteInicial != null){
            predicados.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (taxaFreteFinal != null) {
            predicados.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        /*
            Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
            Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
            Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
         */

        //A clausula WHERE recebe todos os predicados
        criteria.where(predicados.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }


   /*
       public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
            var jpql = "from Restaurante where nome like %:nome% and cozinha.id =: id";
            return manager.createQuery(jpql, Restaurante.class)
                    .setParameter("nome", "%" + nome + "%")
                    .setParameter("taxaInicial", taxaFreteInicial)
                    .setParameter("taxaFinal", taxaFreteFinal)
                    .getResultList();
         }
    */
}
