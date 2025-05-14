package br.com.danilopelusci.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.danilopelusci.model.DocumentoLead;
import br.com.danilopelusci.services.DocumentoLeadService;

@CrossOrigin
@RestController
@RequestMapping("/documentos")
public class DocumentoLeadController {

    @Autowired
    private DocumentoLeadService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumentos(@RequestParam("leadId") Long leadId,
                                              @RequestParam("arquivos") MultipartFile[] arquivos) {
        try {
            documentoService.salvarDocumentos(leadId, arquivos);
            return ResponseEntity.ok("Documentos salvos com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar documentos: " + e.getMessage());
        }
    }
    
    @PostMapping("/upload/documentos")
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
    
    @GetMapping("/download/lead/{id}")
    public ResponseEntity<byte[]> downloadDocumentosZip(@PathVariable Long id) {
        List<DocumentoLead> documentos = documentoService.buscarPorIdlead(id);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            Set<String> nomesAdicionados = new HashSet<>();

            for (DocumentoLead doc : documentos) {
                String nomeOriginal = doc.getNomeArquivo();
                String nomeArquivo = nomeOriginal;

                int contador = 1;
                while (nomesAdicionados.contains(nomeArquivo)) {
                    nomeArquivo = nomeOriginal.replaceFirst("(\\.[^.]*)?$", "_" + contador + "$1"); // adiciona sufixo antes da extensão
                    contador++;
                }

                nomesAdicionados.add(nomeArquivo);

                ZipEntry entry = new ZipEntry(nomeArquivo);
                zos.putNextEntry(entry);
                zos.write(doc.getConteudo());
                zos.closeEntry();
            }

            zos.finish();

            byte[] zipBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documentos_lead_" + id + ".zip");

            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Erro ao gerar ZIP: " + e.getMessage()).getBytes());
        }
    }
    
    @GetMapping("/thumbnail/{id}")
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) {
        DocumentoLead documento = documentoService.buscarPorId(id);

        MediaType tipo = MediaType.parseMediaType(documento.getTipoArquivo());

        return ResponseEntity.ok()
                .contentType(tipo)
                .body(documento.getConteudo());
    }
    
    @GetMapping("/lead/{id}/thumbnails-base64")
    public ResponseEntity<List<Map<String, String>>> listarThumbnailsBase64(@PathVariable Long id) {
        List<DocumentoLead> documentos = documentoService.buscarPorIdlead(id);

        List<Map<String, String>> lista = documentos.stream().map(doc -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", doc.getId().toString());
            map.put("nome", doc.getNomeArquivo());
            map.put("tipo", doc.getTipoArquivo());
            map.put("base64", Base64.getEncoder().encodeToString(doc.getConteudo()));
            return map;
        }).toList();

        return ResponseEntity.ok(lista);
    }
    
}
