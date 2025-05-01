package br.com.danilopelusci.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopelusci.model.LeadHistorico;
import br.com.danilopelusci.repository.LeadHistoricoRepository;

@RestController
@RequestMapping("/lead-historico")
public class LeadHistoricoController {

    @Autowired
    private LeadHistoricoRepository leadHistoricoRepository;

    @PostMapping
    public ResponseEntity<LeadHistorico> create(@RequestBody LeadHistorico leadHistorico) {
        LeadHistorico savedLeadHistorico = leadHistoricoRepository.save(leadHistorico);
        return ResponseEntity.ok(savedLeadHistorico);
    }

    @GetMapping
    public ResponseEntity<List<LeadHistorico>> getAll() {
        return ResponseEntity.ok(leadHistoricoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadHistorico> getById(@PathVariable Long id) {
        Optional<LeadHistorico> leadHistorico = leadHistoricoRepository.findById(id);
        return leadHistorico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadHistorico> update(@PathVariable Long id, @RequestBody LeadHistorico leadHistoricoDetails) {
        return leadHistoricoRepository.findById(id).map(leadHistorico -> {
            leadHistorico.setAcao(leadHistoricoDetails.getAcao());
            leadHistorico.setLead(leadHistoricoDetails.getLead());
            leadHistorico.setUsuario(leadHistoricoDetails.getUsuario());
            LeadHistorico updated = leadHistoricoRepository.save(leadHistorico);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return leadHistoricoRepository.findById(id).map(leadHistorico -> {
            leadHistoricoRepository.delete(leadHistorico);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
