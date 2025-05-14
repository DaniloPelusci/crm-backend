package br.com.danilopelusci.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.danilopelusci.model.DocumentoLead;
import br.com.danilopelusci.model.Lead;
import br.com.danilopelusci.repository.DocumentoLeadRepository;
import br.com.danilopelusci.repository.LeadRepository;
import jakarta.transaction.Transactional;

@Service
public class DocumentoLeadService {

    @Autowired
    private DocumentoLeadRepository documentoRepository;

    @Autowired
    private LeadRepository leadRepository;

    public DocumentoLead salvarDocumento(Long leadId, MultipartFile arquivo) throws IOException {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead não encontrada"));

        DocumentoLead documento = new DocumentoLead();
        documento.setNomeArquivo(arquivo.getOriginalFilename());
        documento.setTipoArquivo(arquivo.getContentType());
        documento.setDataUpload(LocalDateTime.now());
        documento.setConteudo(arquivo.getBytes());
        documento.setLead(lead);

        return documentoRepository.save(documento);
    }
    
    @Transactional
    public void salvarDocumentos(Long leadId, MultipartFile[] arquivos) throws Exception {
    	 Lead lead = leadRepository.findById(leadId)
                 .orElseThrow(() -> new RuntimeException("Lead não encontrada"));
        for (MultipartFile arquivo : arquivos) {
            DocumentoLead doc = new DocumentoLead();
            doc.setLead(lead);
            doc.setNomeArquivo(arquivo.getOriginalFilename());
            doc.setTipoArquivo(arquivo.getContentType());
            doc.setConteudo(arquivo.getBytes());

            documentoRepository.save(doc);
        }
    }

    
    
    public DocumentoLead buscarPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }
    
    public List<DocumentoLead> buscarPorIdlead(Long id) {
        List<DocumentoLead> lista = documentoRepository.findByLeadid(id);
	
        if (lista == null || lista.isEmpty()) {
            throw new RuntimeException("Nenhum documento encontrado para o lead ID: " + id);
        }

        return lista;
    }
}
