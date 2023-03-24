package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGruposController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public List<GrupoDTO> listaGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.valida(usuarioId);
        return grupoDTOAssembler.toCollectionDTO(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionaGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarGrupo(usuarioId, grupoId);
    }
}