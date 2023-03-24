package com.algaworks.algafood.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Grupo {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @ManyToMany
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    @ToString.Exclude
    private Set<Permissao> permissoes = new HashSet<>();

    public void adicionarPermissao(Permissao permissao) {
        getPermissoes().add(permissao);
    }

    public void removerPermissao(Permissao permissao) {
        getPermissoes().remove(permissao);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Grupo grupo = (Grupo) o;
        return id != null && Objects.equals(id, grupo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}