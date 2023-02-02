package com.algaworks.algafood.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @CreationTimestamp
    private LocalDate dataCriacao;
    private LocalDate dataConfirmacao;
    private LocalDate dataCancelamento;
    private LocalDate dataEntrega;

    @Embedded
    private Endereco enderecoEntrega;

    private StatusPedido status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;
    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;


    @OneToMany(mappedBy = "pedido")
    @ToString.Exclude
    private List<ItemPedido> itens = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pedido pedido = (Pedido) o;
        return id != null && Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
