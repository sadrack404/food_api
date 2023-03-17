package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDtoDisassembler;
import com.algaworks.algafood.api.model.UsuarioDto;
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
    UsuarioService usuarioService;
    @Autowired
    UsuarioDtoAssembler usuarioDtoAssembler;
    @Autowired
    UsuarioDtoDisassembler usuarioDtoDisassembler;

    @GetMapping("/{id}")
    public UsuarioDto busca(@PathVariable Long id) {
        Usuario usuario = usuarioService.valida(id);
        return usuarioDtoAssembler.toModel(usuario);
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        return usuarioDtoAssembler.toCollectionModel(usuarioService.listar());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto cadastrar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioDtoDisassembler.toDomainObject(usuarioInput);
        return usuarioDtoAssembler.toModel(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDto atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInputSemSenha usuarioInputSemSenha) {
        Usuario usuario = usuarioService.valida(id);
        usuarioDtoDisassembler.copyToDomainObject(usuarioInputSemSenha, usuario);
        return usuarioDtoAssembler.toModel(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaUsuarioInput senha) {
        usuarioService.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}