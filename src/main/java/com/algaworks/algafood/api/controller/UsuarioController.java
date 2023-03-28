package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioInputSemSenha;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;
    @Autowired
    private UsuarioDTODisassembler usuarioDTODisassembler;

    @GetMapping("/{id}")
    public UsuarioDTO busca(@PathVariable Long id) {
        Usuario usuario = usuarioService.valida(id);
        return usuarioDTOAssembler.toModel(usuario);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioDTOAssembler.toCollectionDTO(usuarioService.listar());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO cadastrar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioDTODisassembler.toDomainObject(usuarioInput);
        return usuarioDTOAssembler.toModel(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInputSemSenha usuarioInputSemSenha) {
        Usuario usuario = usuarioService.valida(id);
        usuarioDTODisassembler.copyToDomainObject(usuarioInputSemSenha, usuario);
        return usuarioDTOAssembler.toModel(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaUsuarioInput senha) {
        usuarioService.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}