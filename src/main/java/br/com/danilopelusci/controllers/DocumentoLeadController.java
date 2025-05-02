package br.com.danilopelusci.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.danilopelusci.model.DocumentoLead;
import br.com.danilopelusci.services.DocumentoLeadService;

@RestController
@RequestMapping("/documentos")
public class DocumentoLeadController {

    @Autowired
    private DocumentoLeadService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(@RequestParam("leadId") Long leadId,
                                             @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            DocumentoLead doc = documentoService.salvarDocumento(leadId, arquivo);
            return ResponseEntity.ok("Documento salvo com sucesso: " + doc.getNomeArquivo());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar documento: " + e.getMessage());
        }
    }
}
