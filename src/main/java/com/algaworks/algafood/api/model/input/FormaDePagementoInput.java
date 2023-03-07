package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaDePagementoInput {
    @NotNull
    private Long id;
    @NotBlank
    private String descricao;
}
