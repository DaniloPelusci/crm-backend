package br.com.danilopelusci.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danilopelusci.model.Lead;
import br.com.danilopelusci.model.Usuario;
import br.com.danilopelusci.repository.LeadRepository;
import br.com.danilopelusci.repository.UsuarioRepository;

@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Lead> listarTodos() { return leadRepository.findAll(); }
    public Optional<Lead> buscarPorId(Long id) { return leadRepository.findById(id); }
    public Lead salvar(Lead lead) { return leadRepository.save(lead); }
    public void excluir(Long id) { leadRepository.deleteById(id); }
	public List<Lead> buscarPorIdUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		return leadRepository.findByUsuario(usuario);
	}
}
