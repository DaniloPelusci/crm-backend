package br.com.danilopelusci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danilopelusci.model.DocumentoLead;

@Repository
public interface DocumentoLeadRepository extends JpaRepository<DocumentoLead, Long>{

}