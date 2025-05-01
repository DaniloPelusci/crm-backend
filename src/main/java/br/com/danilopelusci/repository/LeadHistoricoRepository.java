package br.com.danilopelusci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danilopelusci.model.LeadHistorico;

@Repository
public interface LeadHistoricoRepository extends JpaRepository<LeadHistorico, Long> {
	
}

