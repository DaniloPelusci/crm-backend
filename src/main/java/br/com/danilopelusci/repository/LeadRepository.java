package br.com.danilopelusci.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danilopelusci.model.Lead;
import br.com.danilopelusci.model.Usuario;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
	
	List<Lead> findByUsuario(Usuario usuario);
	
	
}