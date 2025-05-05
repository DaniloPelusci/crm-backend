package br.com.danilopelusci.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocumento(@PathVariable Long id) {
        DocumentoLead doc = documentoService.buscarPorId(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getTipoArquivo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getNomeArquivo() + "\"")
                .body(doc.getConteudo());
    }
}
