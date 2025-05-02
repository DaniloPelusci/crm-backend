package br.com.danilopelusci.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoLeadDTO {
    private Long leadId;
    private MultipartFile arquivo;
    
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public MultipartFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}

    
}