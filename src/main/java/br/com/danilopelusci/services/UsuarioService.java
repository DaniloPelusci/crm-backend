package br.com.danilopelusci.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danilopelusci.model.Usuario;
import br.com.danilopelusci.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() { 
    	return usuarioRepository.findAll(); 
    	}
    public Optional<Usuario> buscarPorId(Long id) { return usuarioRepository.findById(id); }
    public Usuario salvar(Usuario usuario) { return usuarioRepository.save(usuario); }
    public void excluir(Long id) { usuarioRepository.deleteById(id); }
}

