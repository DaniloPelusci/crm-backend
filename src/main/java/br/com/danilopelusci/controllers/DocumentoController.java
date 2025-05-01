package br.com.danilopelusci.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/arquivos")
public class DocumentoController {

    private static final String DIRETORIO_ARQUIVOS = "C:/upload/arquivos/"; // Alterar conforme a necessidade

    @PostMapping("/upload")
    public ResponseEntity<String> uploadArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            // Verifica a extensão do arquivo
            String nomeArquivo = arquivo.getOriginalFilename();
            if (nomeArquivo == null || (!nomeArquivo.endsWith(".pdf") && !nomeArquivo.endsWith(".png"))) {
                return ResponseEntity.badRequest().body("Somente arquivos PDF ou PNG são permitidos.");
            }

            // Cria o diretório se não existir
            File diretorio = new File(DIRETORIO_ARQUIVOS);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Salva o arquivo no diretório
            File arquivoSalvo = new File(diretorio, nomeArquivo);
            arquivo.transferTo(arquivoSalvo);

            return ResponseEntity.ok("Arquivo salvo com sucesso em: " + arquivoSalvo.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}