package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@ValorZeroIncluiDescricao(valorField = "taxaFrete",
        descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @TaxaFrete
    @Multiplo(numero = 2)
    //@DecimalMin(value = "1")
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    // @Valid // valida em formato de cascata
    // @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasDePagamento = new HashSet<>();

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    @CreationTimestamp //Vai Instanciar uma data no momento em que for criada
    @Column(nullable = false, columnDefinition = "datatime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp //Vai Atualizar uma data no momento em que for criada
    @Column(nullable = false, columnDefinition = "datatime")
    private OffsetDateTime dataAtualizacao;
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produto = new ArrayList<>();

    public void ativar() {
        this.ativo = Boolean.TRUE;
    }

    public void inativar() {
        this.ativo = Boolean.FALSE;
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasDePagamento().add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasDePagamento().remove(formaPagamento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Restaurante that = (Restaurante) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}