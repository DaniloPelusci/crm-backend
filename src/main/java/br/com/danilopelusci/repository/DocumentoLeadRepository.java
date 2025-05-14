package br.com.danilopelusci.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.danilopelusci.model.DocumentoLead;

@Repository
public interface DocumentoLeadRepository extends JpaRepository<DocumentoLead, Long>{

	@Query("select doc from DocumentoLead doc where doc.lead.id = :id")
	List<DocumentoLead> findByLeadid(Long id);
	
	
			

}