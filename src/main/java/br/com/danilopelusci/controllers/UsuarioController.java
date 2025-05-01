package br.com.danilopelusci.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopelusci.model.Usuario;
import br.com.danilopelusci.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() { 
    	return usuarioService.listarTodos(); }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Long id) { return usuarioService.buscarPorId(id); }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) { return usuarioService.salvar(usuario); }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) { usuarioService.excluir(id); }
}
